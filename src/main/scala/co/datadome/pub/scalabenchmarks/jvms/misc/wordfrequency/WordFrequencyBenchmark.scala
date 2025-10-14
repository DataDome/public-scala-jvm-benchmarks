package co.datadome.pub.scalabenchmarks.jvms.misc.wordfrequency

import com.ionutbalosin.jvm.performance.benchmarks.miscellaneous.wordfrequency.functional.{ParallelStreamWordFrequency, PatternStreamWordFrequency}
import org.openjdk.jmh.annotations.*

import java.util
import java.util.concurrent.TimeUnit
import scala.collection.mutable


/**
 * Computes the word frequencies/occurrences from a text file.
 * The benchmark uses a few alternative approaches:
 * - iterative
 * - parallel streams
 * - pattern streams
 *
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class WordFrequencyBenchmark {

  final private val CurrentDir: String = System.getProperty("user.dir", ".")
  final private val FileName: String = CurrentDir + "/src/main/resources/word_frequency.txt"

  @Setup
  def setup(): Unit = ()

  @Benchmark
  def iterative(): mutable.Map[String, Int] = {
    Iterative.frequencies(FileName)
  }

  @Benchmark
  def functional(): Map[String, Int] = {
    Functional.frequencies(FileName)
  }

  @Benchmark
  def dac(): Map[String, Int] = {
    Dac.frequencies(FileName)
  }

  @Benchmark
  def parallel_stream(): util.Map[String, java.lang.Long] = {
    ParallelStreamWordFrequency.frequencies(FileName)
  }

  @Benchmark
  def pattern_stream(): util.Map[String, java.lang.Long] = {
    PatternStreamWordFrequency.frequencies(FileName)
  }
}
