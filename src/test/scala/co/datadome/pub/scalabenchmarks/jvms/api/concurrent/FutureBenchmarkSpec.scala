package co.datadome.pub.scalabenchmarks.jvms.api.concurrent

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class FutureBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 1000)(f: FutureBenchmark => A): A = {
    val bench = new FutureBenchmark()
    bench.computationSize = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.run_no_future()
    }
  }

  "run_no_future" in {
    withBench() { bench =>
      bench.run_no_future() shouldBe bench.computation()
    }
  }

  "run_future" in {
    withBench() { bench =>
      bench.run_future() shouldBe bench.computation()
    }
  }
}
