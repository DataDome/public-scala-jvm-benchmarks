package co.datadome.pub.scalabenchmarks.jvms.api.compression

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class GzipCompressionBenchmark {

  private implicit final val random: Random = new Random(16384)

  @Param(Array("1024", "8192", "65536"))
  var size: Int = uninitialized

  private var testData: Array[Byte] = uninitialized

  @Setup
  def setup(): Unit = {
    testData = TestData.generate(size)
  }

  @Benchmark
  def compress(): Array[Byte] = {
    Gzip.compress(testData)
  }

  @Benchmark
  def decompress(): Array[Byte] = {
    val compressed = Gzip.compress(testData)
    Gzip.decompress(compressed)
  }
}
