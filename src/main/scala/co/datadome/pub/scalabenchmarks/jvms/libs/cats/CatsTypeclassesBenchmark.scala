package co.datadome.pub.scalabenchmarks.jvms.libs.cats

import cats.kernel.Monoid
import cats.syntax.all.*
import co.datadome.pub.scalabenchmarks.jvms.utils.*
import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/** Simple benchmarks on Cats-Effect (requiring no parameters) */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class CatsTypeclassesBenchmark {

  private val random = new Random(16384)

  @Param(Array("1000"))
  var size: Int = uninitialized

  var ternaries: Array[Ternary[Int]] = uninitialized
  var result: Array[Ternary[Int]] = uninitialized

  @Setup
  def setup(): Unit = {
    ternaries = Array.fill(size) {
      random.nextInt(3) match {
        case 0 => Ternary.Zero
        case 1 => Ternary.One(random.nextInt(100))
        case 2 => Ternary.Many
      }
    }
    result = Array.fill(size)(Ternary.Zero)
  }

  @Benchmark
  def monoid(): Array[Ternary[Int]] = {
    fastLoop(0, ternaries.length) { ix =>
      result(ix) = Monoid[Ternary[Int]].combine(ternaries(ix), ternaries((ix + 1) % size))
    }
    result
  }

  @Benchmark
  def functor(): Array[Ternary[Int]] = {
    fastLoop(0, ternaries.length) { ix =>
      result(ix) = ternaries(ix).map(_ * 42)
    }
    result
  }

  private def transform(x: Int): Ternary[Int] = {
    if (x % 3 == 0) Ternary.Zero
    else if (x % 3 == 1) Ternary.One(x * 42)
    else Ternary.Many
  }

  @Benchmark
  def monad(): Array[Ternary[Int]] = {
    fastLoop(0, ternaries.length) { ix =>
      result(ix) = ternaries(ix).flatMap(transform)
    }
    result
  }

  @Benchmark
  def forComprehension(): Array[Ternary[Int]] = {
    fastLoop(0, ternaries.length) { ix =>
      result(ix) =
        for {
          a <- ternaries(ix)
          b <- transform(a)
          c = a * 42
          d <- transform(a + 3)
          e = b + d
        } yield e + 1
    }
    result
  }

  @Benchmark
  def foldable(): Int = {
    var sum = 0
    fastLoop(0, ternaries.length) { ix =>
      sum += ternaries(ix).foldLeft(42)(_ + _)
    }
    sum
  }

}
