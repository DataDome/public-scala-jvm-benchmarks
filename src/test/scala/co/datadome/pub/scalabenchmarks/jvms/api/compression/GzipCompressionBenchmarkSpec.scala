package co.datadome.pub.scalabenchmarks.jvms.api.compression

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class GzipCompressionBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int)(f: GzipCompressionBenchmark => A): A = {
    val bench = new GzipCompressionBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(1024) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.compress()
    }
  }

  "compress" in {
    withBench(1024) { bench =>
      val compressed = bench.compress()
      compressed should not be empty
      compressed.length should be < 1024
    }
  }

  "decompress" in {
    withBench(8192) { bench =>
      val decompressed = bench.decompress()
      decompressed should not be empty
      decompressed.length shouldBe 8192
    }
  }

  "round-trip" in {
    withBench(1024) { _ =>
      val testData = Array.fill[Byte](1024)((scala.util.Random.nextInt(256) - 128).toByte)
      val compressed = Gzip.compress(testData)
      val decompressed = Gzip.decompress(compressed)
      decompressed shouldBe testData
    }
  }

  "compression ratio" in {
    withBench(65536) { bench =>
      val compressed = bench.compress()
      compressed.length should be < 65536
      info(s"Gzip compressed size: ${compressed.length}")
    }
  }
}
