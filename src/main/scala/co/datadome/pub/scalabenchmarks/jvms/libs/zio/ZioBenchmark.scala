package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import org.openjdk.jmh.annotations.*
import zio.{Scope as _, *}

import java.io.IOException
import java.util.concurrent.TimeUnit


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ZioBenchmark {

  @Setup
  def setup(): Unit = {

  }

  private def run[A](za: ZIO[Any, Throwable, A]): A = {
    Unsafe.unsafe { implicit unsafe =>
      Runtime.default.unsafe.run(za) match {
        case Exit.Failure(cause) => throw cause.squash
        case Exit.Success(a) => a
      }
    }
  }

  @Benchmark
  def hello_world(): Unit = run {
    Console.printLine("Hello, World!")
  }

  @Benchmark
  def factorial(): BigInt = run {
    ParallelFactorial.factorial(1000)
  }



  // TODO to be continued
}
