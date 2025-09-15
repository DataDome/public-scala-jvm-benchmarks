package co.datadome.pub.scalabenchmarks.jvms.api.collections

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class ListBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 100)(f: ListBenchmark => A): A = {
    val bench = new ListBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.read_size()
    }
  }

  "append" in {
    withBench() { bench =>
      bench.prepend().size shouldBe 100
    }
  }

  "read_size" in {
    withBench() { bench =>
      bench.read_size() shouldBe 100
    }
  }

  "reverse" in {
    withBench() { bench =>
      val v = bench.reverse()
      v.size shouldBe 100
    }
  }

  "iterate" in {
    withBench() { bench =>
      bench.iterate() shouldBe -1281627036
    }
  }

  "foreach" in {
    withBench() { bench =>
      bench.foreach() shouldBe -1281627036
    }
  }

  "for_loop" in {
    withBench() { bench =>
      bench.for_loop() shouldBe -1281627036
    }
  }

  "map" in {
    withBench() { bench =>
      bench.map().size shouldBe 100
    }
  }

  "flatMap" in {
    withBench() { bench =>
      bench.flatMap().size shouldBe 300
    }
  }

  "unapply" in {
    withBench() { bench =>
      bench.unapply() shouldBe -1281627036
    }
  }
}
