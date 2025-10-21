package co.datadome.pub.scalabenchmarks.jvms.api.compression

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class ZstdCompressionBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int, level: Int)(f: ZstdCompressionBenchmark => A): A = {
    val bench = new ZstdCompressionBenchmark()
    bench.size = size
    bench.level = level
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(1024, 3) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.compress()
    }
  }

  "compress" in {
    withBench(1024, 3) { bench =>
      val compressed = bench.compress()
      compressed should not be empty
      compressed.length should be < 1024
    }
  }

  "decompress" in {
    withBench(1024, 3) { bench =>
      val decompressed = bench.decompress()
      decompressed should not be empty
      decompressed.length shouldBe 1024
    }
  }
}
