package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import org.openjdk.jmh.annotations.*
import zio.{Scope as _, System as _}

import java.util.concurrent.TimeUnit


/** Counting word-frequency in a file with ZIO */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ZioWordFrequencyBenchmark {

  final private val CurrentDir: String = System.getProperty("user.dir", ".")
  final private val WordFrequencyFile: String = CurrentDir + "/src/main/resources/word_frequency.txt"

  @Setup
  def setup(): Unit = ()

  @Benchmark
  def wordFrequency(): Map[String, Int] = ZioUtil.run {
    WordFrequency.wordFrequency(WordFrequencyFile)
  }

  @Benchmark
  def wordFrequencyStream(): Map[String, Long] = ZioUtil.run {
    WordFrequency.wordFrequencyStream(WordFrequencyFile)
  }.toMap
}
