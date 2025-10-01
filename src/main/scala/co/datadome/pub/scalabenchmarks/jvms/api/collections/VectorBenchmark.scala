package co.datadome.pub.scalabenchmarks.jvms.api.collections

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/*
 * Tests the most common uses of a Vector
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class VectorBenchmark {

  private val random: Random = new Random(16384)

  @Param(Array("1000"))
  var size: Int = uninitialized

  private var vector: Vector[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    vector = Vector.fill(size)(random.nextInt)
  }

  @Benchmark
  def append(): Vector[Int] = {
    var vector2 = Vector.empty[Int]
    var i = 0
    while (i < size) {
      vector2 = vector2 :+ i
      i += 1
    }
    vector2
  }

  @Benchmark
  def read_size(): Int = {
    vector.size
  }

  @Benchmark
  def reverse(): Vector[Int] = {
    vector.reverse
  }

  @Benchmark
  def iterate(): Int = {
    val it = vector.iterator
    var sum = 0
    while (it.hasNext) {
      sum += it.next()
    }
    sum
  }

  @Benchmark
  def foreach(): Int = {
    var sum = 0
    vector.foreach { i =>
      sum += i
    }
    sum
  }

  @Benchmark
  def for_loop(): Int = {
    var sum = 0
    for (i <- vector) {
      sum += i
    }
    sum
  }

  @Benchmark
  def foldLeft() = {
    vector.foldLeft(0)(_ + _)
  }

  @Benchmark
  def map(): Vector[Int] = {
    vector.map(_ * 2)
  }

  @Benchmark
  def flatMap(): Vector[Int] = {
    vector.flatMap(i => List(i - 1, i, i + 1))
  }

  @Benchmark
  def sequential_access(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      sum += vector(i)
      i += 1
    }
    sum
  }

  @Benchmark
  def random_access(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      sum += vector(7 * i % size) // use a prime number
      i += 1
    }
    sum
  }

}
