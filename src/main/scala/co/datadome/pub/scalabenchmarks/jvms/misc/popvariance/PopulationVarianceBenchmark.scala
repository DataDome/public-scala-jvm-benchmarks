package co.datadome.pub.scalabenchmarks.jvms.misc.popvariance

import org.openjdk.jmh.annotations.*

import java.util.Random
import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized


/**
 * This benchmark generates a population of different ages and then calculates the age variation.
 *
 * Population variance is the average of the distances from each data point in a particular population
 * to the mean squared. It indicates how data points spread out in the population.
 * Population variance is an important measure of dispersion used in statistics.
 *
 * References:
 * - code examples by Mario Fusco (Twitter: @mariofusco)
 * - https://github.com/mariofusco/javaz/tree/master/src/main/java/org/javaz/variance
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class PopulationVarianceBenchmark {

  private val random = new Random(16384)

  // The oldest person ever: 116 years 54 days
  val OldestAge = 116.1479D

  @Param(Array("134217727"))
  var popSize: Int = uninitialized

  private var ages: Array[Double] = uninitialized

  @Setup
  def setup(): Unit = {
    ages = Array.fill(popSize)(random.nextDouble * OldestAge)
  }

  @Benchmark def iterative(): Double = Iterative.variance(ages)

  @Benchmark def functional(): Double = Functional.variance(ages)

}
