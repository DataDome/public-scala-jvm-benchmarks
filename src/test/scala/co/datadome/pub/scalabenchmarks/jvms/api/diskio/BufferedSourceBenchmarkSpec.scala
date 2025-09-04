package co.datadome.pub.scalabenchmarks.jvms.api.diskio

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class BufferedSourceBenchmarkSpec extends AnyFunSuite with Matchers {

  private def withBench[A](bufferSize: Int = 2048, encoding: String = "UTF-8")(f: BufferedSourceBenchmark => A): A = {
    val bench = new BufferedSourceBenchmark()
    // Initialize @Param fields via reflection (JMH doesn't inject in unit tests)
    bench.bufferSize = bufferSize
    bench.encoding = encoding
    bench.setup()
    try f(bench) finally bench.tearDown()
  }

  test("setup works") {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.mkString()
    }
  }

  test("mkString returns the expected result") {
    withBench() { bench =>
      val text = bench.mkString()
      text should startWith("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      text.length should be(13821)
    }
  }

  test("getLines_mkString returns the expected result") {
    withBench() { bench =>
      val text = bench.getLines_mkString()
      text should startWith("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      text.length should be(13821)
    }
  }

  test("reader_readBuffer returns the expected result") {
    withBench() { bench =>
      val buffer = bench.reader_readBuffer()
      buffer.mkString should startWith("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      buffer.length should be(2048)
    }
  }

  test("bufferedReader_readLine_once returns the expected result") {
    withBench() { bench =>
      val line = bench.bufferedReader_readLine_once()
      line should startWith("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      line.length should be(774)
    }
  }
}
