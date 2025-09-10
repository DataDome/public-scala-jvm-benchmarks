package co.datadome.pub.scalabenchmarks.jvms.libs

import org.openjdk.jmh.annotations.*
import zio.{Scope as _, *}

import java.io.IOException
import java.util.concurrent.TimeUnit


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ZioBenchmark {

  @Setup
  def setup(): Unit = {

  }

  @Benchmark
  def hello_world(): Unit = {
    val z: IO[IOException, Unit] = Console.printLine("Hello, World!")
    Unsafe.unsafe { implicit unsafe =>
      Runtime.default.unsafe.run(z) match {
        case Exit.Failure(cause) => throw cause.squash
        case Exit.Success(_) => // do nothing
      }
    }
  }

  // TODO to be continued
}
