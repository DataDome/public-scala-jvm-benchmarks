package co.datadome.pub.scalabenchmarks.jvms.api.random

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class ScalaRandomSizedBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 64)(f: ScalaRandomSizedBenchmark => A): A = {
    val bench = new ScalaRandomSizedBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.next_bytes()
    }
  }

  "next_bytes" in {
    withBench() { bench =>
      bench.next_bytes().length shouldBe 64
    }
  }

  "next_string" in {
    withBench() { bench =>
      bench.next_string().length shouldBe 64
    }
  }
}
