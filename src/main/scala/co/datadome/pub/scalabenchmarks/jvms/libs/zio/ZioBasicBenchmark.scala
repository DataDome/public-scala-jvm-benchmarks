package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit


/** Simple benchmarks on ZIO */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ZioBasicBenchmark {

  @Setup
  def setup(): Unit = ()

  //  @Benchmark
  //  def hello_world(): Unit = ZioUtil.run {
  //    zio.Console.printLine("Hello, World!")
  //  }

  @Benchmark
  def factorial(): BigInt = ZioUtil.run {
    ParallelFactorial.factorial(1000)
  }
}
