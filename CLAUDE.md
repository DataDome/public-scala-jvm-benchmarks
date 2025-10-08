# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

This is a JVM benchmarking repository focused on comparing performance across different JVMs (OpenJDK HotSpot, GraalVM CE, GraalVM Oracle, Azul Prime). All benchmarks are written in Scala 3 and use the Java Microbenchmark Harness (JMH) via the sbt-jmh plugin.

This repository is a fork of [ionutbalosin/jvm-performance-benchmarks](https://github.com/ionutbalosin/jvm-performance-benchmarks), adapted for Scala. The R scripts and shell infrastructure remain largely unchanged from the original.

## Build and Test Commands

### Basic Development
```bash
# Compile the project
sbt compile

# Run tests
sbt test

# Run a specific test
sbt "testOnly *BenchmarkNameSpec"

# Compile benchmarks (generates JMH jar)
sbt Jmh/compile

# Build the benchmark assembly jar
sbt clean Jmh/assembly
```

### Running Benchmarks

```bash
# Run benchmarks for a single JVM (requires configuration first)
./run-benchmarks.sh

# Dry-run mode (shows commands without executing)
./run-benchmarks.sh --dry-run

# Run benchmarks across all configured JVMs
./run-all-benchmarks.sh

# Run in background and redirect output
./background-run-all-benchmarks.sh
```

### Plotting Results

```bash
# Generate plots from benchmark results
# Usage: ./plot-benchmarks.sh <jdk-version> [<arch>]
./plot-benchmarks.sh 21
./plot-benchmarks.sh 21 x86_64
./plot-benchmarks.sh 21 arm64
```

## Configuration

### JVM Setup

Before running benchmarks, configure JVM paths in `settings/config.properties`:
- `OPENJDK_HOTSPOT_VM_HOME` - Path to OpenJDK HotSpot installation
- `GRAAL_VM_CE_HOME` - Path to GraalVM CE installation
- `GRAAL_VM_EE_HOME` - Path to GraalVM Oracle installation
- `AZUL_PRIME_VM_HOME` - Path to Azul Prime VM installation

The file also configures display names, JIT compiler names, and color palettes for plotting.

### Benchmark Suite Configuration

Benchmark suites are defined in JSON files under `settings/`:
- `benchmarks-suite-jdk21.json` - Main benchmark configuration for JDK 21
- Contains global JMH options and per-benchmark settings
- Each benchmark specifies: name, language (java/scala), optional custom JMH options

## Project Structure

```
src/main/scala/co/datadome/pub/scalabenchmarks/jvms/
├── AABaseBenchmark.scala           # Template for new benchmarks
├── InfrastructureBaselineBenchmark.scala  # Infrastructure overhead baseline
├── api/                            # API benchmarks (collections, strings, I/O, etc.)
├── compiler/                       # Compiler optimization benchmarks
├── libs/                           # Third-party library benchmarks
├── misc/                           # Miscellaneous algorithm benchmarks
└── utils/                          # Utility functions for benchmarks

src/test/scala/                     # Tests validating benchmark correctness
```

## Writing Benchmarks

### Benchmark Template

Use `AABaseBenchmark.scala` as a starting point for new benchmarks. Key JMH annotations:
- `@BenchmarkMode(Array(Mode.AverageTime))` - Measure average time
- `@OutputTimeUnit(TimeUnit.NANOSECONDS)` - Time unit for results
- `@Warmup` - Warmup iterations before measurement
- `@Measurement` - Measurement iterations
- `@Fork` - Number of forked JVM processes
- `@State(Scope.Benchmark)` - State scope

### Benchmark Organization

Benchmarks are organized into four categories:
1. **api** - Standard library and API benchmarks (collections, strings, concurrency, I/O, etc.)
2. **compiler** - JIT compiler optimization benchmarks (canonicalization, branch prediction, etc.)
3. **libs** - Third-party library benchmarks (Cats, ZIO, Jsoniter, etc.)
4. **misc** - Algorithm implementations (graph algorithms, mathematical computations, etc.)

### Important Patterns

- Always run `InfrastructureBaselineBenchmark` first to establish overhead baseline
- Use `@CompilerControl` annotations to control inlining behavior
- Use `Blackhole.consume()` to prevent dead code elimination
- Tests in `src/test/scala/` validate that benchmarks produce correct results

## Benchmark Execution Pipeline

The benchmark scripts follow this execution flow:

1. **Load properties** - Read JVM paths and configuration from `settings/config.properties`
2. **Select JVM** - Choose which JVM to benchmark (single JVM mode only)
3. **Configure architecture/OS** - Detect platform (x86_64/arm64, Linux/Mac/Windows)
4. **Configure jq** - Set up JSON parsing tool
5. **Compile benchmarks** - Run `sbt clean Jmh/assembly` to build JMH jar
6. **Configure JVM** - Set JAVA_HOME and validate JVM availability
7. **Configure JMH** - Parse benchmark suite JSON, set output folders
8. **Run benchmarks** - Execute each benchmark via JMH jar

Results are stored in: `results/jdk-<version>/<arch>/jmh/<jvm-identifier>/`

## Plotting and Analysis

The plotting pipeline (executed via `plot-benchmarks.sh`):

1. **Pre-process results** - Merge/split benchmark result files for better visualization
2. **Calculate geometric mean** - Compute normalized geometric mean per category
3. **Generate plots** - Create SVG plots using R and ggplot2

Output locations:
- Raw results: `results/jdk-<version>/<arch>/jmh/`
- Geometric means: `results/jdk-<version>/<arch>/geomean/`
- Plot SVGs: `results/jdk-<version>/<arch>/plot/`

## Dependencies

The project uses:
- **Scala 3.7.3** (latest version, not LTS)
- **JMH** via sbt-jmh plugin
- **Cats Core 2.13.0** - Functional programming abstractions
- **Cats Effect 3.6.3** - Effect system for async/concurrent programs
- **FS2 3.12.2** - Streaming library
- **ZIO 2.1.21** - Alternative effect system
- **ScalaTest 3.2.19** - Testing framework
- **Jsoniter 2.32.0** - JSON serialization library

## Scala Compiler Options

The project uses strict compiler settings:
- `-no-indent` - Disable significant indentation
- `-feature`, `-deprecation`, `-unchecked` - Enable comprehensive warnings
- `-explain` - Detailed type error explanations
- `-Wunused:*` - Warn on all unused code
- `-Wvalue-discard` - Warn on discarded non-Unit results

Java compatibility: Source and target set to Java 21.
