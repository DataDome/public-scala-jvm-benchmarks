# Function to load a library and install if not available
loadLibrary <- function(name) {
  if (!require(name, character.only = TRUE)) {
    install.packages(name, repos = "http://cran.us.r-project.org")
    library(name, character.only = TRUE)
  }
}

# Load the necessary libraries
loadLibrary("ggplot2")
loadLibrary("svglite")
loadLibrary("styler")
loadLibrary("plyr")
loadLibrary("psych")
loadLibrary("tools")

# Apply styles to all R and/or Rmd files in the directory
style_dir()

# Use a large positive value like 999 to prevent scientific notation
options(scipen = 999)

# Read the CSV results from file
readCsvResultsFromFile <- function(file_path) {
  result <- data.frame()

  tryCatch(
    {
      csv_file <- file(file_path, "r")
      result <- read.csv(csv_file, sep = ",", header = TRUE)
    },
    warning = function(w) {
      # cat("Cannot read from", file_path, "File skipped.\n")
    },
    error = function(e) {
      # cat("Cannot read from", file_path, "File skipped.\n")
    },
    finally = {
      if (exists("csv_file")) {
        close(csv_file)
      }
    }
  )

  return(result)
}

# Write the CSV results to file
writeDataToCsvFile <- function(path, file, data) {
  tryCatch(
    {
      if (!dir.exists(path)) {
        dir.create(path)
      }
      csv_file <- file(file.path(path, file), "w")
      write.table(data, csv_file, sep = ",", row.names = FALSE)
    },
    warning = function(w) {
      cat("Warning: ", conditionMessage(w), "\n")
      cat("Cannot write to", file.path(path, file), "File skipped.\n")
    },
    error = function(e) {
      cat("Error: ", conditionMessage(e), "\n")
      cat("Cannot write to", file.path(path, file), "File skipped.\n")
    },
    finally = {
      if (exists("csv_file")) {
        close(csv_file)
      }
    }
  )
}
