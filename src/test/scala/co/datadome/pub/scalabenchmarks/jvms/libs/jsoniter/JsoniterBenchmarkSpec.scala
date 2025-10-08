package co.datadome.pub.scalabenchmarks.jvms.libs.jsoniter

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class JsoniterBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 1000)(f: JsoniterBenchmark => A): A = {
    val bench = new JsoniterBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.serializeToBytes()
    }
  }

  "serializeToBytes" in {
    withBench() { bench =>
      val bytes = bench.serializeToBytes()
      bytes.length should be > 0
    }
  }

  "serializeToString" in {
    withBench() { bench =>
      val json = bench.serializeToString()
      json.length should be > 0
      json should include("FirstName")
      json should include("lastName")
    }
  }

  "deserializeFromBytes" in {
    withBench() { bench =>
      val people = bench.deserializeFromBytes()
      people.size shouldBe bench.size
      people.head.firstName should startWith("FirstName")
    }
  }

  "deserializeFromString" in {
    withBench() { bench =>
      val people = bench.deserializeFromString()
      people.size shouldBe bench.size
      people.head.lastName should startWith("LastName")
    }
  }

}
