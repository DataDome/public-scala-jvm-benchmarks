# Load the necessary utilities
source("./scripts/ggplot2/utils.r")

# Function to split benchmark results from a single data frame based on a specified column and values
splitBenchmarkResults <- function(path, benchmark_file, column_name, column_values) {
  result <- data.frame()

  benchmark_file_path <- file.path(path, benchmark_file)
  cat("Splitting", benchmark_file_path, "benchmark ...\n")
  data <- readCsvResultsFromFile(benchmark_file_path)

  # Check if the specified column exists in the data frame
  if (column_name %in% colnames(data)) {
    result <- data[data[[column_name]] %in% column_values, ]
  }

  return(result)
}

# Function to split and write benchmark results to an output file
splitAndWriteBenchmarkResultsToFile <- function(jmh_output_folder, jvm_identifier, benchmark_file, column_name, column_values, output_file) {
  benchmark_base_path <- file.path(jmh_output_folder, jvm_identifier)
  data <- splitBenchmarkResults(benchmark_base_path, benchmark_file, column_name, column_values)

  if (nrow(data) > 0) {
    output_path <- file.path(benchmark_base_path, output_file)
    writeDataToCsvFile(benchmark_base_path, output_file, data)
  }
}
