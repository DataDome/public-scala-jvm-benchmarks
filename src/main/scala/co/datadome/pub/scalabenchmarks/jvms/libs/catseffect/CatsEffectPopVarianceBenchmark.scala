package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/** Population variance benchmark using Cats-Effect */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class CatsEffectPopVarianceBenchmark {

  private val random = new Random(16384)

  // The oldest person ever: 116 years 54 days
  val OldestAge = 116.1479D

  @Param(Array("134217727"))
  var popSize: Int = uninitialized

  private var ages: Vector[Double] = uninitialized

  @Setup
  def setup(): Unit = {
    ages = Vector.fill(popSize)(random.nextDouble * OldestAge)
  }

  @Benchmark
  def variance(): Double = CatsUtil.run {
    ParallelVariance.variance(ages)
  }
}
