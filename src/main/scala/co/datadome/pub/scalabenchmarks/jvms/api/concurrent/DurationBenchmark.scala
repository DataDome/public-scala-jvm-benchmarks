package co.datadome.pub.scalabenchmarks.jvms.api.concurrent

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.*


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class DurationBenchmark {
  
  
  private final val TenMinutes = 10.minutes
  private final val OneHour = 1.hour

  @Benchmark
  def instantiation(): FiniteDuration = {
    10.minutes
  }

  @Benchmark
  def add_finite(): FiniteDuration = {
    TenMinutes + OneHour
  }

  @Benchmark
  def add_infinite() = {
    TenMinutes + Duration.Inf
  }

  @Benchmark
  def mult_finite() = {
    TenMinutes * 3
  }

  @Benchmark
  def mult_infinite() = {
    Duration.Inf * 3
  }
}
