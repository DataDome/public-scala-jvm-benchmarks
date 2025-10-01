package co.datadome.pub.scalabenchmarks.jvms.api.collections

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized


/*
 * Tests the most common uses of a Set
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class SetAddBenchmark {

  @Param(Array("1000"))
  var size: Int = uninitialized

  @Setup
  def setup(): Unit = ()

  @Benchmark
  def add_different(): Set[Int] = {
    var set = Set.empty[Int]
    var i = 0
    while (i < size) {
      set = set + i
      i += 1
    }
    set
  }

  @Benchmark
  def add_same(): Set[Int] = {
    var set = Set.empty[Int]
    var i = 0
    while (i < size) {
      set = set + (i % 10)
      i += 1
    }
    set
  }

}
