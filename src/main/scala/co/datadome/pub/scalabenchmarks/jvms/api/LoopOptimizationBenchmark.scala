package co.datadome.pub.scalabenchmarks.jvms.api

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random
import co.datadome.pub.scalabenchmarks.jvms.utils.*


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class LoopOptimizationBenchmark {

  private val random = new Random

  @Param(Array("100", "1000"))
  var size: Int = uninitialized

  private var array: Array[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    array = Array.fill(size)(random.nextInt)
  }

  @Benchmark
  def while_range(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      sum += i
      i += 1
    }
    sum
  }

  @Benchmark
  def for_range(): Int = {
    var sum = 0
    for (i <- 0 until size) {
      sum += i
    }
    sum
  }

  @Benchmark
  def while_array(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      sum += array(i)
      i += 1
    }
    sum
  }

  @Benchmark
  def for_array(): Int = {
    var sum = 0
    for (n <- array) {
      sum += n
    }
    sum
  }

  @Benchmark
  def macro_fastForeach_array(): Int = {
    var sum = 0
    array.fastForeach { n =>
      sum += n
    }
    sum
  }

  @Benchmark
  def macro_fastLoop(): Int = {
    var sum = 0
    fastLoop(0, size) { n =>
      sum += n
    }
    sum
  }

}
