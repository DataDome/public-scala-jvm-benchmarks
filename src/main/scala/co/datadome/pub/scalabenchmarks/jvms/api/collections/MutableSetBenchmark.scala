package co.datadome.pub.scalabenchmarks.jvms.api.collections

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.collection.mutable
import scala.compiletime.uninitialized
import scala.util.Random


/*
 * Tests the most common uses of a Set
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class MutableSetBenchmark {

  private val random: Random = new Random(16384)
  private val MaxValue = 100
  private val AlmostMaxValue = 90

  @Param(Array("100", "1000"))
  var size: Int = uninitialized

  private var set: mutable.Set[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    set = mutable.Set.fill(size)(random.nextInt(MaxValue))
  }

  @Benchmark
  def add(): mutable.Set[Int] = {
    var i = 0
    while (i < size) {
      set += i
      i += 1
    }
    set
  }

  @Benchmark
  def read_size(): Int = {
    set.size
  }

  @Benchmark
  def iterate(): Int = {
    val it = set.iterator
    var sum = 0
    while (it.hasNext) {
      sum += it.next()
    }
    sum
  }

  @Benchmark
  def foreach(): Int = {
    var sum = 0
    set.foreach { i =>
      sum += i
    }
    sum
  }

  @Benchmark
  def for_loop(): Int = {
    var sum = 0
    for (i <- set) {
      sum += i
    }
    sum
  }

  @Benchmark
  def map(): mutable.Set[Int] = {
    set.map(_ * 2)
  }

  @Benchmark
  def flatMap(): mutable.Set[Int] = {
    set.flatMap(i => List(i - 1, i, i + 1))
  }

  @Benchmark
  def contains_many(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      if (set.contains(i % MaxValue)) {
        sum += i
      }
      i += 1
    }
    sum
  }

  @Benchmark
  def contains_few(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      if (set.contains(i % MaxValue + AlmostMaxValue)) {
        sum += i
      }
      i += 1
    }
    sum
  }


}
