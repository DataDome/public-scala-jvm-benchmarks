# Load the necessary utilities
source("./scripts/ggplot2/utils.r")

# Merge all benchmark results for different JIT results into a single data frame
# and append a new column "<column_name>":"<column_values>" as a differentiator
mergeBenchmarkResults <- function(path, benchmark_file_paths, column_name, column_values) {
  result <- data.frame()

  for (i in seq_along(benchmark_file_paths)) {
    benchmark_file_path <- file.path(path, benchmark_file_paths[i])
    cat("Merging", benchmark_file_path, "benchmark ...\n")

    data <- readCsvResultsFromFile(benchmark_file_path)

    if (nrow(data) > 0) {
      data[, column_name] <- column_values[i]
      result <- rbind(result, data)
    }
  }

  # To prevent the "all connections are in use" error that can occur when dealing with multiple file connections and not closing them properly
  closeAllConnections()

  return(result)
}

# Merge and write to a single output file multiple benchmark results for different JIT results, corresponding to a single JVM
mergeAndWriteBenchmarkResultsToFile <- function(jmh_output_folder, jvm_identifier, benchmark_file_paths, column_name, column_values, output_file) {
  if (length(benchmark_file_paths) != length(column_values)) {
    cat("Error: the number of files must be equal to the number of columns\n")
    return(NULL)
  }

  benchmark_base_path <- file.path(jmh_output_folder, jvm_identifier)
  data <- mergeBenchmarkResults(benchmark_base_path, benchmark_file_paths, column_name, column_values)

  if (nrow(data) > 0) {
    writeDataToCsvFile(benchmark_base_path, output_file, data)
  }
}
