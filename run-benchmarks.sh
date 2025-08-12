#!/bin/bash

time_converter() {
  if [[ -z $1 || $1 -lt 60 ]]; then
    min=0
    secs="$1"
  else
    time_min=$(bc <<<"scale=2; $1/60")
    min=${time_min%.*}
    secs=$(bc <<<"scale=2; ($time_min - $min) * 60" | awk '{print int($1+0.5)}')
  fi

  echo ""
  echo "Elapsed: ${min} minutes and ${secs} seconds."
}

default_if_empty() {
  value="$1"
  default_value="$2"

  if [[ -z "$value" || "$value" == "null" ]]; then
    echo "$default_value"
  else
    echo "$value"
  fi
}

remove_spaces() {
  echo "$1" | xargs echo -n | tr -s ' '
}

create_folder() {
  folder="$1"

  if [ -d "$folder" ]; then
    echo "WARNING: Folder '$folder' already exists. Existing output benchmarks might be overwritten."
  else
    echo "Creating folder: '$folder' ..."
    mkdir -p "$folder"
  fi
}

run_benchmark() {
  JVM_OPTS=$(remove_spaces "$1")
  TEST_NAME=$(remove_spaces "$2")
  JMH_OPTS=$(remove_spaces "$3")
  JVM_ARGS_APPEND=$(remove_spaces "$4")
  CMD="java $JVM_OPTS -jar \"$JMH_JAR\" \"\\\\.$TEST_NAME\\\\.\" $JMH_OPTS -jvmArgsAppend \"$JVM_ARGS_APPEND\""

  echo ""
  echo "Running $TEST_NAME benchmark ..."
  date
  echo "$CMD"
  echo ""

  if [ "$DRY_RUN" != "--dry-run" ]; then
    eval "$CMD"
  fi
}

run_benchmark_suite() {
  echo "Running $JVM_NAME test suite ..."

  no_of_benchmarks=$(./$JQ -r ".benchmarks | length" <"$JMH_BENCHMARKS")
  global_jmh_opts=$(./$JQ -r ".globals.jmhOpts" <"$JMH_BENCHMARKS")
  global_jvm_opts=$(./$JQ -r ".globals.jvmOpts" <"$JMH_BENCHMARKS")
  global_jvm_args_append=$(./$JQ -r ".globals.jvmArgsAppend" <"$JMH_BENCHMARKS")

  create_folder "$JMH_OUTPUT_FOLDER"

  test_suite_start=$(date +%s)

  counter=0
  while [ $counter -lt $no_of_benchmarks ]; do
    bench_name=$(./$JQ --argjson counter "$counter" -r ".benchmarks[$counter].name" <"$JMH_BENCHMARKS")
    bench_lang=$(./$JQ --argjson counter "$counter" -r ".benchmarks[$counter].lang" <"$JMH_BENCHMARKS")
    bench_lang=$(default_if_empty "$bench_lang" "java")
    bench_output_file_name=$(./$JQ --argjson counter "$counter" -r ".benchmarks[$counter].outputFileName" <"$JMH_BENCHMARKS")
    bench_output_file_name=$(default_if_empty "$bench_output_file_name" "$bench_name.$bench_lang")
    bench_jmh_opts=$(./$JQ --argjson counter "$counter" -r ".benchmarks[$counter].jmhOpts" <"$JMH_BENCHMARKS")
    bench_jmh_opts=$(default_if_empty "$bench_jmh_opts" "")
    bench_jvm_args_append=$(./$JQ --argjson counter "$counter" -r ".benchmarks[$counter].jvmArgsAppend" <"$JMH_BENCHMARKS")
    bench_jvm_args_append=$(default_if_empty "$bench_jvm_args_append" "")
    global_jmh_opts_upd="${global_jmh_opts/((outputFilePath))/${JMH_OUTPUT_FOLDER}/${bench_output_file_name}}"

    run_benchmark "$global_jvm_opts" "$bench_name" "$global_jmh_opts_upd $bench_jmh_opts" "$global_jvm_args_append $bench_jvm_args_append"

    ((counter++))
  done

  echo ""
  echo "Finished $JVM_NAME test suite!"

  elapsed_time_seconds=$(($(date +%s) - test_suite_start))
  time_converter "$elapsed_time_seconds"
}

compile_benchmark_suite() {
  CMD="sbt clean Jmh/assembly"

  echo "Compiling benchmark suite..."
  echo "$CMD"
  echo ""

  if eval "$CMD"; then
    echo "Compilation completed successfully."
  else
    echo "ERROR: Compilation failed. Unable to continue!"
    return 1
  fi
}

echo ""
echo "#################################################################"
echo "#######       JVM Performance Benchmarks Test Suite       #######"
echo "#################################################################"
DRY_RUN="$1"
if [ "$DRY_RUN" == "--dry-run" ]; then
  echo ""
  echo "+==========================================================+"
  echo "| Running in dry-run mode. No benchmarks will be executed. |"
  echo "+==========================================================+"
fi

echo ""
echo "+=====================+"
echo "| [0/8] JVM Selection |"
echo "+=====================+"
. ./scripts/shell/select-jvm.sh

echo ""
echo "+=======================+"
echo "| [1/8] Load Properties |"
echo "+=======================+"
. ./scripts/shell/load-properties.sh

echo ""
echo "+=============================+"
echo "| [2/8] Hardware Architecture |"
echo "+=============================+"
. ./scripts/shell/configure-arch.sh

echo ""
echo "+========================+"
echo "| [3/8] OS Configuration |"
echo "+========================+"
. ./scripts/shell/configure-os.sh || exit 1
. ./scripts/shell/configure-os-$OS.sh "$DRY_RUN"

echo ""
echo "+========================+"
echo "| [4/8] JQ Configuration |"
echo "+========================+"
. ./scripts/shell/configure-jq.sh || exit 1

echo ""
echo "+===============================+"
echo "| [5/8] Compile benchmark suite |"
echo "+===============================+"
if ! compile_benchmark_suite; then
  exit 1
fi

echo ""
echo "+=========================+"
echo "| [6/8] JVM Configuration |"
echo "+=========================+"
. ./scripts/shell/configure-jvm.sh || exit 1

echo ""
echo "+=========================+"
echo "| [7/8] JMH Configuration |"
echo "+=========================+"
. ./scripts/shell/configure-jmh.sh || exit 1

echo ""
echo "+===========================+"
echo "| [8/8] Run benchmark suite |"
echo "+===========================+"
run_benchmark_suite
