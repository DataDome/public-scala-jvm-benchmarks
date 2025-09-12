package co.datadome.pub.scalabenchmarks.jvms.api.collections

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/*
 * Tests the most common uses of a List
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ListBenchmark {

  private val random: Random = new Random(16384)

  @Param(Array("100", "1000"))
  var size: Int = uninitialized

  private var list: List[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    list = List.fill(size)(random.nextInt)
  }

  @Benchmark
  def prepend(): List[Int] = {
    var list2: List[Int] = Nil
    var i = 0
    while (i < size) {
      list2 = i :: list2
      i += 1
    }
    list2
  }

  @Benchmark
  def read_size(): Int = {
    list.size
  }

  @Benchmark
  def reverse(): List[Int] = {
    list.reverse
  }

  @Benchmark
  def iterate(): Int = {
    val it = list.iterator
    var sum = 0
    while (it.hasNext) {
      sum += it.next()
    }
    sum
  }

  @Benchmark
  def foreach(): Int = {
    var sum = 0
    list.foreach { i =>
      sum += i
    }
    sum
  }

  @Benchmark
  def for_loop(): Int = {
    var sum = 0
    for (i <- list) {
      sum += i
    }
    sum
  }

  @Benchmark
  def foldLeft() = {
    list.foldLeft(0)(_ + _)
  }

  @Benchmark
  def map(): List[Int] = {
    list.map(_ * 2)
  }

  @Benchmark
  def flatMap(): List[Int] = {
    list.flatMap(i => List(i - 1, i, i + 1))
  }

  @Benchmark
  def unapply() = {
    var sum = 0
    var i = 0
    var reminder = list
    while (i < size) {
      reminder match {
        case Nil => sum += 0
        case h :: t => sum += h; reminder = t
      }
      i += 1
    }
    sum
  }


}
