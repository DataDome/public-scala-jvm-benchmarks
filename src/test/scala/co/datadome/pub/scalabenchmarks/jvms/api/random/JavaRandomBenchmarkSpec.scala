package co.datadome.pub.scalabenchmarks.jvms.api.random

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class JavaRandomBenchmarkSpec extends TestSuite {

  private def withBench[A](f: JavaRandomBenchmark => A): A = {
    val bench = new JavaRandomBenchmark()
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.next_boolean()
    }
  }

  "simple method calls" in {
    // There's not much point in testing the results here, so we just check that nothing crashes
    withBench { bench =>
      bench.next_boolean()
      bench.next_int()
      bench.next_long()
      bench.next_double()
      bench.next_float()
      bench.next_gaussian()
    }
  }
}
