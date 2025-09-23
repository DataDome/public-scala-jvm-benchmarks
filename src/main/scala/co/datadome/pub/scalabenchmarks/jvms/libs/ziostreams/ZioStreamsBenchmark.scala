package co.datadome.pub.scalabenchmarks.jvms.libs.ziostreams

import org.openjdk.jmh.annotations.*
import zio.{Scope as _, System as _}

import java.util.concurrent.TimeUnit


/** Simple benchmarks on ZStream (requiring no parameters) */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ZioStreamsBenchmark {

  final private val CurrentDir: String = System.getProperty("user.dir", ".")
  final private val WordFrequencyFile: String = CurrentDir + "/src/main/resources/word_frequency.txt"

  @Setup
  def setup(): Unit = ()

  @Benchmark
  def wordFrequency(): Map[String, Long] = ZioUtil.run {
    WordFrequency.wordFrequency(WordFrequencyFile)
  }.toMap
}
