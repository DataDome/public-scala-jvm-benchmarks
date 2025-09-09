package co.datadome.pub.scalabenchmarks.jvms.api.string

import com.ionutbalosin.jvm.performance.benchmarks.api.string.utils.StringUtils
import com.ionutbalosin.jvm.performance.benchmarks.api.string.utils.StringUtils.generateCharArray
import org.openjdk.jmh.annotations.*

import java.lang.String.valueOf
import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random

/**
 * Benchmark measuring the performance of various concatenation methods using different data types
 * (e.g., String, int, float, char, long, double, boolean, Object):
 * - StringBuilder
 * - StringBuffer
 * - String.concat()
 * - plus operator
 * - Template
 *
 * The input String and char contain characters encoded in either Latin-1 or UTF-16.
 *
 * Note: The benchmark might encompass different allocations, potentially impacting the overall results.
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class StringConcatenationBenchmark {
  // Recommended command line options:
  // - JMH options: -prof gc

  // TODO Make the benchmark deterministic: StringUtils should take a random instead of using its internal one

  final private val random = new Random(16384)

  private var aString: String = null
  private var anInt: Int = uninitialized
  private var aFloat: Float = uninitialized
  private var aChar: Char = uninitialized
  private var aLong: Long = uninitialized
  private var aDouble: Double = uninitialized
  private var aBool: Boolean = uninitialized
  private var anObject: AnyRef = uninitialized

  @Param(Array("128"))
  var length: Int = uninitialized

  @Param
  var coder: StringUtils.Coder = uninitialized

  @Setup
  def setup(): Unit = {
    aString = new String(StringUtils.generateCharArray(length, coder))
    anInt = random.nextInt
    aFloat = random.nextFloat
    aChar = random.nextPrintableChar()
    aLong = random.nextLong
    aDouble = random.nextDouble
    aBool = random.nextBoolean
    anObject = random.nextBytes(1)
  }

  @Benchmark
  def string_builder(): String = {
    // Do not explicitly set a capacity
    (new StringBuilder).append(aString).append(anInt).append(aFloat).append(aChar).append(aLong).append(aDouble).append(aBool).append(anObject).toString
  }

  @Benchmark
  def java_string_builder(): String = {
    // Do not explicitly set a capacity
    (new java.lang.StringBuilder).append(aString).append(anInt).append(aFloat).append(aChar).append(aLong).append(aDouble).append(aBool).append(anObject).toString
  }

  @Benchmark
  def java_string_buffer(): String = {
    // Do not explicitly set a capacity
    (new java.lang.StringBuffer).append(aString).append(anInt).append(aFloat).append(aChar).append(aLong).append(aDouble).append(aBool).append(anObject).toString
  }

  @Benchmark
  def string_concat(): String = "".concat(aString).concat(valueOf(anInt)).concat(valueOf(aFloat)).concat(valueOf(aChar)).concat(valueOf(aLong)).concat(valueOf(aDouble)).concat(valueOf(aBool)).concat(valueOf(anObject))

  @Benchmark
  def plus_operator(): String = aString + anInt + aFloat + aChar + aLong + aDouble + aBool + anObject

  @Benchmark
  def template(): String =
    s"$aString$anInt$aFloat$aChar$aLong$aDouble$aBool$anObject"

}
