package co.datadome.pub.scalabenchmarks.jvms.libs.fs2

import cats.effect.IO
import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit


/** Simple benchmarks on Cats-Effect (requiring no parameters) */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class Fs2Benchmark {

  final private val CurrentDir: String = System.getProperty("user.dir", ".")
  final private val WordFrequencyFile: String = CurrentDir + "/src/main/resources/word_frequency.txt"

  @Setup
  def setup(): Unit = ()
  
  @Benchmark
  def wordFrequency(): Map[String, Int] = Fs2Util.run {
    WordFrequency.wordFrequency(WordFrequencyFile)
  }.head
}
