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
class Lz4CompressionBenchmark {

  private implicit final val random: Random = new Random(16384)

  @Param(Array("1024", "8192", "65536"))
  var size: Int = uninitialized

  private var testData: Array[Byte] = uninitialized
  private var compressedDataFast: Array[Byte] = uninitialized
  private var compressedDataHigh: Array[Byte] = uninitialized

  @Setup
  def setup(): Unit = {
    testData = TestData.generate(size)
    compressedDataFast = Lz4.compressFast(testData)
    compressedDataHigh = Lz4.compressHigh(testData)
  }

  @Benchmark
  def compress_fast(): Array[Byte] = {
    Lz4.compressFast(testData)
  }

  @Benchmark
  def compress_high(): Array[Byte] = {
    Lz4.compressHigh(testData)
  }

  @Benchmark
  def decompress_fastToFast(): Array[Byte] = {
    Lz4.decompressFast(compressedDataFast, size)
  }

  @Benchmark
  def decompress_fastToSafe(): Array[Byte] = {
    Lz4.decompressFast(compressedDataFast, size)
  }

  @Benchmark
  def decompress_highToFast(): Array[Byte] = {
    Lz4.decompressFast(compressedDataHigh, size)
  }

  @Benchmark
  def decompress_highToSafe(): Array[Byte] = {
    Lz4.decompressFast(compressedDataHigh, size)
  }
  
}
