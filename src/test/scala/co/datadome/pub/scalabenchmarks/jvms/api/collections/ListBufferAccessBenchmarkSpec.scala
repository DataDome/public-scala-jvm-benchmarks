package co.datadome.pub.scalabenchmarks.jvms.api.collections

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class ListBufferAccessBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 100)(f: ListBufferAccessBenchmark => A): A = {
    val bench = new ListBufferAccessBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.sequential_access()
    }
  }

  "sequential_access" in {
    withBench() { bench =>
      bench.sequential_access() shouldBe -1281627036
    }
  }

  "random_access" in {
    withBench() { bench =>
      bench.random_access() shouldBe -1281627036
    }
  }

  "sequential_update" in {
    withBench() { bench =>
      val buf = bench.sequential_update()
      buf.size shouldBe 100
    }
  }

  "random_update" in {
    withBench() { bench =>
      val buf = bench.random_update()
      buf.size shouldBe 100
    }
  }
}
