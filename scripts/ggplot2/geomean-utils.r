# Load the necessary utilities
source("./scripts/ggplot2/utils.r")

# Apply column sanitizations on the data frame
sanitizeBenchmarkData <- function(data) {
  # Delete the rows containing profile stats in the Benchmark name (e.g., gc:·gc.alloc.rate)
  data <- data[!grepl(":.", data$Benchmark), ]

  # Replace commas with dots for Score and Error columns for consistency
  data$Score <- as.numeric(gsub(",", ".", data$Score))

  # Keep only the necessary data frame columns for plotting
  data <- data[, grep("^(Benchmark|Score|Unit)$", colnames(data))]

  return(data)
}

# Sanitize and merge all benchmark results for different benchmark files into a single data frame
sanitizeAndMergeBenchmarkResults <- function(jmh_output_folder, jvm_identifier, benchmark_files) {
  result <- data.frame()

  for (benchmark_file in benchmark_files) {
    benchmark_file_basename <- tools::file_path_sans_ext(benchmark_file)

    benchmark_file_path <- file.path(jmh_output_folder, jvm_identifier, benchmark_file)
    data <- readCsvResultsFromFile(benchmark_file_path)
    if (!is.null(data)) {
      data <- sanitizeBenchmarkData(data)
      result <- rbind(result, data)
    }
  }

  # To prevent the "all connections are in use" error that can occur when dealing with multiple file connections and not closing them properly
  closeAllConnections()

  return(result)
}

# Convert all average time scores to "ns/op" (for consistency across all benchmark results)
standardizeBenchmarkTimeUnits <- function(data) {
  # Define conversion factors
  sec_to_ns <- 1e9
  ms_to_ns <- 1e6
  us_to_ns <- 1e3

  # Convert "sec/op" to "ns/op"
  data$Score[data$Unit == "sec/op"] <- as.numeric(data$Score[data$Unit == "sec/op"]) * sec_to_ns
  data$Unit[data$Unit == "sec/op"] <- "ns/op"

  # Convert "ms/op" to "ns/op"
  data$Score[data$Unit == "ms/op"] <- as.numeric(data$Score[data$Unit == "ms/op"]) * ms_to_ns
  data$Unit[data$Unit == "ms/op"] <- "ns/op"

  # Convert "us/op" to "ns/op"
  data$Score[data$Unit == "us/op"] <- as.numeric(data$Score[data$Unit == "us/op"]) * us_to_ns
  data$Unit[data$Unit == "us/op"] <- "ns/op"

  return(data)
}

# Calculate the geometric mean report (e.g., the geomean score and the total number of benchmarks) for average time scores
calculateGeometricMeanReport <- function(jmh_output_folder, jvm_identifier, benchmark_files) {
  data <- sanitizeAndMergeBenchmarkResults(jmh_output_folder, jvm_identifier, benchmark_files)
  data <- standardizeBenchmarkTimeUnits(data)
  cat("The", jvm_identifier, "category contains", nrow(data), "benchmarks\n")

  if (nrow(data) > 0) {
    geomean <- geometric.mean(data$Score)
  } else {
    geomean <- NA
  }

  return(list("geomean" = geomean, "benchmarks" = nrow(data)))
}
