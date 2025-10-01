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
class ListBufferAccessBenchmark {

  private val random: Random = new Random(16384)

  @Param(Array("100", "1000"))
  var size: Int = uninitialized

  private var buffer: mutable.ListBuffer[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    buffer = mutable.ListBuffer.fill(size)(random.nextInt)
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
