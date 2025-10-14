package co.datadome.pub.scalabenchmarks.jvms.api.collections

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ArrayBenchmark {

  private val random: Random = new Random(16384)

  @Param(Array("100"))
  var sizeX: Int = uninitialized

  @Param(Array("100"))
  var sizeY: Int = uninitialized

  @Benchmark
  def tabulate2(): Array[Array[Int]] = {
    Array.tabulate(sizeX, sizeY) { (x, y) => x * y }

  }
}
