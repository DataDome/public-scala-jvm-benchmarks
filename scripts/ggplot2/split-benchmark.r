# Load the necessary utilities
source("./scripts/ggplot2/split-utils.r")

# Retrieve command line arguments in a very specific order
args <- commandArgs(TRUE)
if (length(args) != 5) {
  stop("Usage: Rscript script.R <jmh_output_folder>
        <openjdk_hotspot_vm_identifier> <graalvm_ce_identifier> <graalvm_ee_identifier> <azul_prime_vm_identifier>")
}
jmh_output_folder <- args[1]
jvm_identifiers <- args[2:5]

# Define a function to split and process benchmarks
splitAndProcessBenchmark <- function(benchmark_file, param_name, column_values) {
  column_name <- paste("Param..", param_name, sep = "")
  for (jvm_identifier in jvm_identifiers) {
    benchmark_name <- sub("\\.csv$", "", benchmark_file)
    output_file <- paste(
      benchmark_name,
      "param",
      param_name,
      paste(column_values, sep = "_"),
      sep = "_"
    )
    output_file <- paste(output_file, ".csv", sep = "")
    splitAndWriteBenchmarkResultsToFile(jmh_output_folder, jvm_identifier, benchmark_file, column_name, column_values, output_file)
  }
}

# API benchmarks #######################################################################################################

splitAndProcessBenchmark("LoopOptimizationBenchmark.scala.csv", "size", list("100"))
splitAndProcessBenchmark("LoopOptimizationBenchmark.scala.csv", "size", list("1000"))

# Collections benchmarks
splitAndProcessBenchmark("ListBenchmark.scala.csv", "size", list("100"))
splitAndProcessBenchmark("ListBenchmark.scala.csv", "size", list("1000"))
splitAndProcessBenchmark("ListBufferBenchmark.scala.csv", "size", list("100"))
splitAndProcessBenchmark("ListBufferBenchmark.scala.csv", "size", list("1000"))
splitAndProcessBenchmark("MutableSetBenchmark.scala.csv", "size", list("100"))
splitAndProcessBenchmark("MutableSetBenchmark.scala.csv", "size", list("1000"))
splitAndProcessBenchmark("SetBenchmark.scala.csv", "size", list("100"))
splitAndProcessBenchmark("SetBenchmark.scala.csv", "size", list("1000"))
splitAndProcessBenchmark("VectorBenchmark.scala.csv", "size", list("100"))
splitAndProcessBenchmark("VectorBenchmark.scala.csv", "size", list("1000"))

# Random benchmarks
splitAndProcessBenchmark("JavaRandomBenchmark.scala.csv", "size", list("16"))
splitAndProcessBenchmark("JavaRandomBenchmark.scala.csv", "size", list("256"))
splitAndProcessBenchmark("ScalaRandomBenchmark.scala.csv", "size", list("16"))
splitAndProcessBenchmark("ScalaRandomBenchmark.scala.csv", "size", list("256"))


# Misc benchmarks ######################################################################################################

# Split and process FactorialBenchmark
splitAndProcessBenchmark("FactorialBenchmark.scala.csv", "n", list("1000"))
splitAndProcessBenchmark("FactorialBenchmark.scala.csv", "n", list("5000"))


# Old benchmarks #######################################################################################################

# Split and process FibonacciBenchmark
splitAndProcessBenchmark("FibonacciBenchmark.csv", "n", list("500"))
splitAndProcessBenchmark("FibonacciBenchmark.csv", "n", list("3000"))

# Split and process NpeControlFlowBenchmark
splitAndProcessBenchmark("NpeControlFlowBenchmark.csv", "nullThreshold", list("0"))
splitAndProcessBenchmark("NpeControlFlowBenchmark.csv", "nullThreshold", list("16"))

# Split and process StackTraceBenchmark
splitAndProcessBenchmark("StackTraceBenchmark.csv", "stackDepth", list("1"))
splitAndProcessBenchmark("StackTraceBenchmark.csv", "stackDepth", list("10"))
splitAndProcessBenchmark("StackTraceBenchmark.csv", "stackDepth", list("100"))
splitAndProcessBenchmark("StackTraceBenchmark.csv", "stackDepth", list("1000"))

# Split and process VPThreadSynchronizationBenchmark
splitAndProcessBenchmark("VPThreadSynchronizationBenchmark.csv", "backoffType", list("NONE"))
splitAndProcessBenchmark("VPThreadSynchronizationBenchmark.csv", "backoffType", list("SLEEP"))
splitAndProcessBenchmark("VPThreadSynchronizationBenchmark.csv", "backoffType", list("PARK"))

# To prevent the "all connections are in use" error that can occur when dealing with multiple file connections and not closing them properly
closeAllConnections()
