package co.datadome.pub.scalabenchmarks.jvms.api.collections

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
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
class ListBufferBenchmark {

  private val random: Random = new Random(16384)

  @Param(Array("100", "1000"))
  private var size: Int = uninitialized

  private var buffer: mutable.ListBuffer[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    buffer = mutable.ListBuffer.fill(size)(random.nextInt)
  }

  @Benchmark
  def prepend(): mutable.ListBuffer[Int] = {
    var i = 0
    while (i < size) {
      buffer.prepend(i)
      i += 1
    }
    buffer
  }

  @Benchmark
  def append(): mutable.ListBuffer[Int] = {
    var i = 0
    while (i < size) {
      buffer.append(i)
      i += 1
    }
    buffer
  }

  @Benchmark
  def read_size(): Int = {
    buffer.size
  }

  @Benchmark
  def reverse(): mutable.ListBuffer[Int] = {
    buffer.reverse
  }

  @Benchmark
  def iterate(): Int = {
    val it = buffer.iterator
    var sum = 0
    while (it.hasNext) {
      sum += it.next()
    }
    sum
  }

  @Benchmark
  def foreach(): Int = {
    var sum = 0
    buffer.foreach { i =>
      sum += i
    }
    sum
  }

  @Benchmark
  def for_loop(): Int = {
    var sum = 0
    for (i <- buffer) {
      sum += i
    }
    sum
  }

  @Benchmark
  def map(): mutable.ListBuffer[Int] = {
    buffer.map(_ * 2)
  }

  @Benchmark
  def flatMap(): mutable.ListBuffer[Int] = {
    buffer.flatMap(i => List(i - 1, i, i + 1))
  }

  @Benchmark
  def sequential_access(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      sum += buffer(i)
      i += 1
    }
    sum
  }

  @Benchmark
  def random_access(): Int = {
    var i = 0
    var sum = 0
    while (i < size) {
      sum += buffer(3 * i % size)
      i += 1
    }
    sum
  }

  @Benchmark
  def sequential_update(): ListBuffer[Int] = {
    var i = 0
    while (i < size) {
      buffer(i) = i
      i += 1
    }
    buffer
  }

  @Benchmark
  def random_update(): ListBuffer[Int] = {
    var i = 0
    while (i < size) {
      buffer(3 * i % size) = i
      i += 1
    }
    buffer
  }

}
