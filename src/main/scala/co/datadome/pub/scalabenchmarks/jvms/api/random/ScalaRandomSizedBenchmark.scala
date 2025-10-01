package co.datadome.pub.scalabenchmarks.jvms.api.random

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random

/** Assess the basic pseudorandom generator in Scala. Benchmarks methods which requires a size. */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ScalaRandomSizedBenchmark {

  @Param(Array("1024"))
  var size: Int = uninitialized

  private var random: Random = uninitialized
  private var bytes: Array[Byte] = uninitialized

  @Setup
  def setup(): Unit = {
    bytes = new Array(size)
    random = new Random(42)
  }

  @Benchmark
  def next_bytes(): Array[Byte] = {
    random.nextBytes(bytes)
    bytes
  }

  @Benchmark
  def next_string(): String = {
    random.nextString(size)
  }

  // Those methods exist on Java Random but not Scala Random
  //
  //  @Benchmark
  //  def doubles: DoubleStream = {
  //    ???
  //  }
  //
  //  @Benchmark
  //  def ints: IntStream = {
  //    ???
  //  }
  //
  //  @Benchmark
  //  def longs: LongStream = {
  //    ???
  //  }


}
