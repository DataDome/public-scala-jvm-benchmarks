package co.datadome.pub.scalabenchmarks.jvms.api.collections

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class SetBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 100)(f: SetBenchmark => A): A = {
    val bench = new SetBenchmark()
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

  "read_size" in {
    withBench() { bench =>
      bench.read_size() shouldBe 62
    }
  }

  "iterate" in {
    withBench() { bench =>
      bench.iterate() shouldBe 3065
    }
  }

  "foreach" in {
    withBench() { bench =>
      bench.foreach() shouldBe 3065
    }
  }

  "for_loop" in {
    withBench() { bench =>
      bench.for_loop() shouldBe 3065
    }
  }

  "map" in {
    withBench() { bench =>
      bench.map().size shouldBe 44
    }
  }

  "flatMap" in {
    withBench() { bench =>
      bench.flatMap().size shouldBe 96
    }
  }

  "contains_many" in {
    withBench() { bench =>
      bench.contains_many() shouldBe 3065
    }
  }

  "contains_few" in {
    withBench() { bench =>
      bench.contains_few() shouldBe 34
    }
  }
}
