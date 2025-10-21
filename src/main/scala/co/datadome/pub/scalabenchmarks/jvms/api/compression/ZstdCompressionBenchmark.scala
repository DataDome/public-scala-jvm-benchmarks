package co.datadome.pub.scalabenchmarks.jvms.api.compression

import org.openjdk.jmh.annotations.*

import com.github.luben.zstd.Zstd
import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ZstdCompressionBenchmark {

  private implicit final val random: Random = new Random(16384)

  @Param(Array("1024", "8192", "65536"))
  var size: Int = uninitialized

  @Param(Array("1", "3", "9", "19"))
  var level: Int = uninitialized

  private var testData: Array[Byte] = uninitialized
  private var compressedData: Array[Byte] = uninitialized

  @Setup
  def setup(): Unit = {
    testData = TestData.generate(size)
    compressedData = Zstd.compress(testData, level)
  }

  @Benchmark
  def compress(): Array[Byte] = {
    Zstd.compress(testData, level)
  }

  @Benchmark
  def decompress(): Array[Byte] = {
    Zstd.decompress(compressedData, size)
  }
}
