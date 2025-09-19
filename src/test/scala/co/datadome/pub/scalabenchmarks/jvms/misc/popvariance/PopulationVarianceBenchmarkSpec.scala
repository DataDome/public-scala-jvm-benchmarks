package co.datadome.pub.scalabenchmarks.jvms.misc.popvariance

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife.GameOfLifeBenchmark

class PopulationVarianceBenchmarkSpec extends TestSuite {
  val Tolerance = 0.000000001d

  private def withBench[A](size: Int = 134217727)(f: PopulationVarianceBenchmark => A): A = {
    val bench = new PopulationVarianceBenchmark
    bench.popSize = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(10000) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.iterative()
    }
  }

  "iterative and functional should give the same result" in {
    withBench(10000) { bench =>
      val a = bench.iterative()
      val b = bench.functional()

      Math.abs(a - b) should be <= Tolerance
    }
  }

}
