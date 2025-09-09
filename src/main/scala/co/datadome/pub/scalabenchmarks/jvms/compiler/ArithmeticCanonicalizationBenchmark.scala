package co.datadome.pub.scalabenchmarks.jvms.compiler

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized

/*
 * This benchmark checks whether the compiler performs arithmetic canonicalization, a process that
 * involves transforming arithmetic expressions into a canonical form. This transformation includes
 * restructuring expressions to a common, simplified form. Canonical forms are easier to analyze and
 * optimize, potentially leading to better code generation and improved performance.
 *
 * Note: While replacing multiple operations with one, the canonicalized expression might even be
 * more expensive than one of the original operations.
 */

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class ArithmeticCanonicalizationBenchmark {

  @Param(Array("true"))
  var isHeavy: Boolean = uninitialized

  // a big prime number
  @Param(Array("179426549"))
  var value: Long = uninitialized

  @Benchmark
  def shift(): Long = {
    doShift()
  }

  @Benchmark
  def add(): Long = {
    doAdd()
  }

  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  def doAdd(): Long = {
    val a = this.value
    if (isHeavy) {
      a + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a
        + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a
        + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a
        + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a + a
      // 64 additions
    } else a
  }

  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  def doShift(): Long = {
    val a = this.value
    if (isHeavy) a << 6 else a
  }
}
