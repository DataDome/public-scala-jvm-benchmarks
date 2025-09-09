package co.datadome.pub.scalabenchmarks.jvms.compiler

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class IfConditionalBranchBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 128)(f: IfConditionalBranchBenchmark => A): A = {
    val bench = new IfConditionalBranchBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.no_if_branch()
    }
  }

  "no_if_branch" in {
    withBench() { bench =>
      bench.no_if_branch() shouldBe 259716
    }
  }

  "predictable_if_branch" in {
    withBench() { bench =>
      bench.predictable_if_branch() shouldBe 259716
    }
  }

  "unpredictable_if_branch" in {
    withBench() { bench =>
      bench.unpredictable_if_branch() shouldBe 59046
    }
  }
}
