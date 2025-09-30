package co.datadome.pub.scalabenchmarks.jvms.libs.cats

import cats.data.EitherT
import co.datadome.pub.scalabenchmarks.jvms.utils.*
import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized


/** Simple benchmarks on Cats-Effect (requiring no parameters) */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class CatsTypesBenchmark {

  @Param(Array("1000"))
  var size: Int = uninitialized

  var result: Array[Ternary[Int]] = uninitialized

  @Setup
  def setup(): Unit = {
    result = Array.fill(size)(Ternary.Zero)
  }

  private def half(i: Int): Ternary[Either[String, Int]] = {
    if (i % 2 == 0) Ternary.One(Right(i / 2))
    else Ternary.One(Left("odd"))
  }

  private def third(i: Int): Ternary[Either[String, Int]] = {
    if (i % 3 == 0) Ternary.One(Right(i / 3))
    else if (i > 30) Ternary.Many
    else Ternary.One(Left("too big"))
  }

  @Benchmark
  def eitherT(): Array[Ternary[Int]] = {
    fastLoop(0, size) { i =>
      val eitherT = for {
        a <- EitherT(half(i))
        b <- EitherT(third(i))
      } yield a + b
      result(i) = eitherT.value match {
        case Ternary.One(Left(error)) => Ternary.Zero
        case Ternary.One(Right(value)) => Ternary.One(value)
        case Ternary.Zero => Ternary.Zero
        case Ternary.Many => Ternary.Many
      }
    }
    result
  }

}
