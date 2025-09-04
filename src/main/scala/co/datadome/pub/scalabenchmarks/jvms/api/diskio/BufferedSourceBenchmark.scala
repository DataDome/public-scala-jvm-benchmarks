package co.datadome.pub.scalabenchmarks.jvms.api.diskio

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.io.BufferedSource


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class BufferedSourceBenchmark {

  private val Space: String = "\\s+";
  private val CurrentDir: String = System.getProperty("user.dir", ".");
  private val FileName: String = CurrentDir + "/src/main/resources/lorem_ipsum.txt";

  @Param(Array("2048"))
  private var bufferSize = uninitialized

  @Param(Array("ISO-8859-1", "UTF-8"))
  private var encoding: String = uninitialized

  private var charBuffer: Array[Char] = uninitialized
  private var source: BufferedSource = uninitialized

  @Setup
  def setup(): Unit = {
    charBuffer = new Array[Char](bufferSize)
    source = scala.io.Source.fromFile(FileName, encoding)
  }

  @TearDown
  def tearDown(): Unit = {
    source.close()
  }

  @Benchmark
  def from_file(): BufferedSource = {
    val source2 = scala.io.Source.fromFile(FileName)
    source2.close()
    source2
  }

  @Benchmark
  def mkString(): String = {
    source.mkString
  }

  @Benchmark
  def getLines_mkString(): String = {
    source.getLines().mkString
  }

  @Benchmark
  def reader_readBuffer(): Array[Char] = {
    val reader = source.reader()
    reader.read(charBuffer)
    charBuffer
  }

  @Benchmark
  def bufferedReader_readLine_once(): String = {
    val reader = source.bufferedReader()
    reader.readLine()
  }
}
