package co.datadome.pub.scalabenchmarks.jvms.misc.factorial

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized


/*
 * Calculates the factorial of a number using a few alternative approaches:
 * - array-based multiplication approach
 * - BigInteger multiplication approach
 * - divide-and-conquer multiplication approach using a fork-join pool
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class FactorialBenchmark {

  @Param(Array("1000", "5000"))
  var n: Int = uninitialized

  @Setup
  def setup(): Unit = ()

  // TODO Finish

  //  @Benchmark
  //  def iterative_array_multiply(): Array[Byte] = {
  //    iterativeArrayMultiply.factorial()
  //  }

  @Benchmark
  def iterative_big_int_multiply(): BigInt = {
    IterativeBigIntMultiply.factorial(n)
  }

  @Benchmark
  def dac_big_int_multiply(): BigInt = {
    DacBigIntMultiply.factorial(n)
  }

}
