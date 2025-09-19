package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.effect.IO
import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit


/** Simple benchmarks on Cats-Effect (requiring no parameters) */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class CatsEffectBasicBenchmark {

  @Setup
  def setup(): Unit = ()

  @Benchmark
  def hello_world(): Unit = CatsUtil.run {
    IO.println("Hello, World!")
  }

  @Benchmark
  def factorial(): BigInt = CatsUtil.run {
    ParallelFactorial.factorial(1000)
  }



  // TODO to be continued

  //  @Benchmark
  //  def fizzBuzz(): Unit = {
  //    // TODO Improve
  //    val run =
  //      for {
  //        ctr <- IO.ref(0)
  //        poll = ctr.get
  //        _ <- poll.flatMap(IO.println(_)).foreverM.start
  //        _ <- poll.map(_ % 3 == 0).ifM(IO.println("fizz"), IO.unit).foreverM.start
  //        _ <- poll.map(_ % 5 == 0).ifM(IO.println("buzz"), IO.unit).foreverM.start
  //        _ <- ctr.update(_ + 1).foreverM.void
  //      } yield ()
  //
  //    run.unsafeRunAsync {
  //      case Left(error) => throw error
  //      case Right(_) => // do nothing
  //    }
  //  }

}
