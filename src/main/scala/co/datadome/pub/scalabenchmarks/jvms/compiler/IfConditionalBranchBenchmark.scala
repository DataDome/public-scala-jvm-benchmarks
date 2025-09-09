package co.datadome.pub.scalabenchmarks.jvms.compiler

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/*
 * Tests the conditional branch optimizations within a loop using:
 * - a predictable branch pattern
 * - an unpredictable branch pattern
 * - no branch at all
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class IfConditionalBranchBenchmark {

  private val random: Random = new Random(16384)
  private val threshold = 4096

  private var array: Array[Int] = uninitialized

  @Param(Array("16384"))
  var size: Int = uninitialized

  @Setup
  def setup(): Unit = {
    array = new Array[Int](size)
    for (i <- 0 until size) {
      // all values are within [0, threshold)
      array(i) = random.nextInt(threshold)
    }
  }

  @Benchmark
  def no_if_branch(): Int = {
    var sum = 0
    var i = 0
    while (i < size) {
      val value = array(0)
      sum += value
      i += 1
    }
    sum
  }

  // all values are less than the THRESHOLD, therefore the condition is true and the branch is
  // always taken. This could be equivalent or very close to no_if_branch()
  @Benchmark
  def predictable_if_branch(): Int = {
    var sum = 0
    var i = 0
    while (i < size) {
      val value = array(0)
      if (value < threshold) sum += value
      i += 1
    }
    sum
  }

  // some values are bigger and some are smaller than THRESHOLD / 2, making this condition
  // unpredictable
  @Benchmark
  def unpredictable_if_branch(): Int = {
    var sum = 0
    var i = 0
    while (i < size) {
      val value = array(0)
      if (value <= (threshold / 2)) sum += value
      i += 1
    }
    sum
  }
}
