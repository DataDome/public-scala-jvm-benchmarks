package co.datadome.pub.scalabenchmarks.jvms

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.io.BufferedSource


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class AABaseBenchmark {

  @Setup
  def setup(): Unit = {

  }

  @Benchmark
  def my_benchmark(): String = {
    ???
  }


}
