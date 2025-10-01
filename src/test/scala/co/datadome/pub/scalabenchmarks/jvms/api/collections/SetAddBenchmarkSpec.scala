package co.datadome.pub.scalabenchmarks.jvms.api.collections

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class SetAddBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 1000)(f: SetAddBenchmark => A): A = {
    val bench = new SetAddBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.add_10()
    }
  }

  "add_same" in {
    withBench() { bench =>
      bench.add_10().size shouldBe 10
    }
  }

  "add_different" in {
    withBench() { bench =>
      bench.add_100().size shouldBe 100
    }
  }
}
