package co.datadome.pub.scalabenchmarks.jvms


import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

import java.util.concurrent.TimeUnit


/*
 * This benchmark is used as a baseline (i.e., a preliminary check) to assess the infrastructure overhead for the code to measure.
 * Since no magical infrastructures are incurring no overhead, it is essential to know what default overheads might occur in our setup.
 * It measures the calls performance of empty methods (w/ and w/o explicit inlining) but also the performance of
 * returning an object versus consuming it via black holes. All of these mechanisms are used by the benchmark suite.
 *
 * This is particularly useful in case of a comparison between different types of JVMs, and it should be run
 * before any other real benchmark to check the default costs.
 *
 * Note: A comparison between different JVMs might not be further relevant unless, at least, the baseline is the same.
 *
 * References:
 * - https://github.com/openjdk/jmh/blob/master/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_01_HelloWorld.java
 * - https://github.com/openjdk/jmh/blob/master/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_16_CompilerControl.java
 * - https://shipilev.net/jvm/anatomy-quarks/27-compiler-blackholes
 */

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class InfrastructureBaselineBenchmark {

  // $ java -jar */*/benchmarks.jar ".*InfrastructureBaselineBenchmark.*"
  // Recommended command line options:
  // - JMH options: -prof {Linux: perfnorm, Mac OS X: dtraceasm, Windows: xperfasm}

  /*
   * The performance of below methods should be the same:
   * - method_baseline()
   * - method_blank()
   * - method_inline()
   *
   * The cost of method_dont_inline() is slightly higher.
   */

  var that: AnyRef = null

  @Benchmark
  def method_baseline(): Unit = {
    // this method was intentionally left blank
  }

  @Benchmark
  def method_blank(): Unit = {
    target_blank()
  }

  @Benchmark
  def method_inline(): Unit = {
    target_inline()
  }

  @Benchmark
  def method_dont_inline(): Unit = {
    target_dont_inline()
  }

  /*
   * The performance of below methods should be the same:
   * - obj_return()
   * - obj_blackhole_consume()
   *
   * The cost of obj_sink() is slightly higher.
   */

  @Benchmark
  def obj_return(): Unit = {
    return that: Unit
  }

  @Benchmark
  def obj_blackhole_consume(bh: Blackhole): Unit = {
    bh.consume(that)
  }

  @Benchmark
  def obj_sink(): Unit = {
    sink(that)
  }

  private def target_blank(): Unit = {
    // this method was intentionally left blank
  }

  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  private def target_dont_inline(): Unit = {
    // this method was intentionally left blank
  }

  @CompilerControl(CompilerControl.Mode.INLINE)
  private inline def target_inline(): Unit = {
    // this method was intentionally left blank
  }

  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  private def sink(a: AnyRef): Unit = {
    // this method was intentionally left blank
  }
}
