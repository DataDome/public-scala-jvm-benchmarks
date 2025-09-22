package co.datadome.pub.scalabenchmarks.jvms.misc.palindrome

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.io.Source


/*
 * Iterates through a list of Strings read from a file and checks, for each String, if it is a palindrome.
 * The benchmark uses a few alternative approaches:
 * - trampolines
 * - recursive
 * - iterative
 *
 * The trampoline pattern is used for implementing algorithms recursively but without
 * blowing the stack (as an alternative to recursive functions).
 * A trampoline is an iteration applying a list of functions, where each function returns
 * the next function to be called.
 *
 * The result (i.e., number of palindromes) is compared against a known constant number
 * to be sure the computation is not wrong.
 *
 * References:
 * - code examples by Mario Fusco (Twitter: @mariofusco)
 * - https://github.com/mariofusco/javaz/tree/master/src/main/java/org/javaz/trampoline
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class PalindromeBenchmark {


  private val CurrentDir = System.getProperty("user.dir", ".")
  private val FileName = CurrentDir + "/src/main/resources/palindrome.list"

  @Setup
  def setup(): Unit = {
  }

  @Benchmark
  def iterative(): Long = palindromes(IterativePredicate.isPalindrome)

  @Benchmark
  def functional(): Long = palindromes(FunctionalPredicate.isPalindrome)

  @Benchmark
  def recursive(): Long = palindromes(RecursivePredicate.isPalindrome)

  private inline def palindromes(inline predicate: String => Boolean): Long = {
    val source = Source.fromFile(FileName)
    try source.getLines().count(predicate)
    finally source.close()
  }

}
