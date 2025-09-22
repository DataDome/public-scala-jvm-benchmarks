package co.datadome.pub.scalabenchmarks.jvms.misc.primes

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.util.Random


/**
 * Calculates the count of prime numbers up to a specified threshold (e.g., N).
 * The benchmark employs several alternative methods:
 * - Sieve of Eratosthenes
 * - Trial division (i.e., checking if a number is prime by dividing it by all possible divisors up to the square root of the number.)
 * - Miller-Rabin primality test
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class PrimesBenchmark {

  private given Random = new Random(16384)

  @Param(Array("8388608"))
  var n = 0

  @Setup
  def setup(): Unit = ()

  @Benchmark
  def trial_division_iterative(): Long = IterativeTrialDivision.primes(n)

  @Benchmark
  def trial_division_functional(): Long = FunctionalTrialDivision.primes(n)

  @Benchmark
  def eratosthenes_sieve(): Long = EratosthenesSieve.primes(n)

  @Benchmark
  def miller_rabin(): Long = MillerRabin.primes(n)
}
