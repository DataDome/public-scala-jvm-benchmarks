package co.datadome.pub.scalabenchmarks.jvms.api

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class EnumLookupBenchmarkSpec extends TestSuite {

  private def withBench[A](f: EnumLookupBenchmark => A): A = {
    val bench = new EnumLookupBenchmark()
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.enum_values()
    }
  }

  "enum_values" in {
    withBench { bench =>
      val res = bench.enum_values()
      res shouldBe EnumLookupBenchmark.Car.Koenigsegg
    }
  }

  "cached_enum_values" in {
    withBench { bench =>
      val res = bench.cached_enum_values()
      res shouldBe EnumLookupBenchmark.Car.Koenigsegg
    }
  }
}
