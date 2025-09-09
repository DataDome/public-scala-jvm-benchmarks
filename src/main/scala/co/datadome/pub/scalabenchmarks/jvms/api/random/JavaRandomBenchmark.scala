package co.datadome.pub.scalabenchmarks.jvms.api.random

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized;

/** Assess the basic pseudorandom generator in Java. */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class JavaRandomBenchmark {

  @Param(Array("128"))
  var size: Int = uninitialized

  private var javaRandom: java.util.Random = uninitialized
  private var bytes: Array[Byte] = uninitialized

  @Setup
  def setup(): Unit = {
    bytes = new Array(size);
    javaRandom = new java.util.Random(42)
  }

  @Benchmark
  def next_boolean(): Boolean = {
    javaRandom.nextBoolean()
  }

  @Benchmark
  def next_bytes(): Array[Byte] = {
    javaRandom.nextBytes(bytes);
    bytes
  }

  @Benchmark
  def next_float(): Float = {
    javaRandom.nextFloat()
  }

  @Benchmark
  def next_double(): Double = {
    javaRandom.nextDouble()
  }

  @Benchmark
  def next_int(): Int = {
    javaRandom.nextInt()
  }

  @Benchmark
  def next_long(): Long = {
    javaRandom.nextLong()
  }

  @Benchmark
  def next_gaussian(): Double = {
    javaRandom.nextGaussian()
  }

  // Those methods are non-existent in Scala, so no point benchmarking them to compare
  //  @Benchmark
  //  def next_exponential: Double = {
  //    javaRandom.nextExponential()
  //  }
  //
  //  @Benchmark
  //  def doubles: DoubleStream = {
  //    javaRandom.doubles(bytesSize)
  //  }
  //
  //  @Benchmark
  //  def ints: IntStream = {
  //    javaRandom.ints(bytesSize)
  //  }
  //
  //  @Benchmark
  //  def longs: LongStream = {
  //    javaRandom.longs(bytesSize)
  //  }


}
