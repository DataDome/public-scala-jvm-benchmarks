package co.datadome.pub.scalabenchmarks.jvms.api.compression

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class Lz4CompressionBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int)(f: Lz4CompressionBenchmark => A): A = {
    val bench = new Lz4CompressionBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(1024) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.compress_fast()
    }
  }

  "compress_fast" in {
    withBench(8192) { bench =>
      val compressed = bench.compress_fast()
      compressed should not be empty
      // LZ4 is optimized for speed, may not compress very small data well
      compressed.length should be < 8192
    }
  }

  "compress_high" in {
    withBench(8192) { bench =>
      val compressed = bench.compress_high()
      compressed should not be empty
      // LZ4 is optimized for speed, may not compress very small data well
      compressed.length should be < 8192
    }
  }

  "decompress_fastToFast" in {
    withBench(8192) { bench =>
      val decompressed = bench.decompress_fastToFast()
      decompressed should not be empty
      decompressed.length shouldBe 8192
    }
  }

  "decompress_fastToSafe" in {
    withBench(8192) { bench =>
      val decompressed = bench.decompress_fastToSafe()
      decompressed should not be empty
      decompressed.length shouldBe 8192
    }
  }

  "decompress_highToFast" in {
    withBench(8192) { bench =>
      val decompressed = bench.decompress_highToFast()
      decompressed should not be empty
      decompressed.length shouldBe 8192
    }
  }

  "decompress_highToSafe" in {
    withBench(8192) { bench =>
      val decompressed = bench.decompress_highToSafe()
      decompressed should not be empty
      decompressed.length shouldBe 8192
    }
  }

  "round-trip fast-to-fast" in {
    withBench(1024) { _ =>
      val testData = Array.fill[Byte](1024)((scala.util.Random.nextInt(256) - 128).toByte)
      val compressed = Lz4.compressFast(testData)
      val decompressed = Lz4.decompressFast(compressed, testData.length)
      decompressed shouldBe testData
    }
  }

  "round-trip fast-to-safe" in {
    withBench(1024) { _ =>
      val testData = Array.fill[Byte](1024)((scala.util.Random.nextInt(256) - 128).toByte)
      val compressed = Lz4.compressFast(testData)
      val decompressed = Lz4.decompressSafe(compressed, testData.length)
      decompressed shouldBe testData
    }
  }

  "round-trip high-to-fast" in {
    withBench(1024) { _ =>
      val testData = Array.fill[Byte](1024)((scala.util.Random.nextInt(256) - 128).toByte)
      val compressed = Lz4.compressHigh(testData)
      val decompressed = Lz4.decompressFast(compressed, testData.length)
      decompressed shouldBe testData
    }
  }

  "round-trip high-to-safe" in {
    withBench(1024) { _ =>
      val testData = Array.fill[Byte](1024)((scala.util.Random.nextInt(256) - 128).toByte)
      val compressed = Lz4.compressHigh(testData)
      val decompressed = Lz4.decompressSafe(compressed, testData.length)
      decompressed shouldBe testData
    }
  }
}
