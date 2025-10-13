package co.datadome.pub.scalabenchmarks.jvms.api.concurrent

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.concurrent.duration.*
import scala.concurrent.{Await, Future}


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class FutureBenchmark {

  val Timeout: FiniteDuration = 10.seconds

  @Param(Array("1000", "10000"))
  var computationSize: Int = uninitialized

  var records: Array[Int] = uninitialized

  @Setup
  def setup(): Unit = {
    records = (0 to computationSize).map(_ * 13).toArray
  }

  def computation(): Int = {
    var i = 0
    var res = 0
    while (i < records.length) {
      if (i % 3 == 2) {
        res += records(i)
      } else if (i % 3 == 1) {
        res -= records(i)
      } else {
        res *= records(i)
      }
      i += 1
    }
    res
  }


  @Benchmark
  def run_future(): Int = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val f: Future[Int] = Future(computation())
    Await.result(f, Timeout)
  }

  @Benchmark
  def run_no_future(): Int = {
    computation()
  }
}
