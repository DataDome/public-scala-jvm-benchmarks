package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import co.datadome.pub.scalabenchmarks.jvms.misc.popvariance.PopulationVarianceBenchmark

class CatsEffectPopVarianceBenchmarkSpec extends TestSuite {
  val Tolerance = 0.000000001d

  private def withBench[A](size: Int)(f: CatsEffectPopVarianceBenchmark => A): A = {
    val bench = new CatsEffectPopVarianceBenchmark()
    bench.popSize = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(10000) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.variance()
    }
  }

  "variance" in {
    withBench(10000) { bench =>
      val result = bench.variance()

      /* Uses the same seed and algorithm to generate the population, so it should produce the same result */
      val otherBench = new PopulationVarianceBenchmark
      otherBench.popSize = 10000
      otherBench.setup()
      val expected = otherBench.iterative()

      result should be(expected +- Tolerance)
    }
  }

}
