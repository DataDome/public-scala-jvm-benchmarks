package co.datadome.pub.scalabenchmarks.jvms.api.string

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import com.ionutbalosin.jvm.performance.benchmarks.api.string.utils.StringUtils

class StringConcatenationBenchmarkSpec extends TestSuite {

  private def withBench[A](length: Int = 16, coder: StringUtils.Coder = StringUtils.Coder.LATIN1)(f: StringConcatenationBenchmark => A): A = {
    val bench = new StringConcatenationBenchmark()
    bench.length = length
    bench.coder = coder
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.string_builder()
    }
  }

  // Prefix of the string is different on each run because StringUtils uses an internal random to generate a String
  // Suffix of the string is different on each run because it's the memory address of an array

  "string_builder" in {
    withBench() { bench =>
      bench.string_builder() should include("11307240360.655293355-47967987206613007610.5050586525587465true")
    }
  }

  "java_string_builder" in {
    withBench() { bench =>
      bench.java_string_builder() should include("11307240360.655293355-47967987206613007610.5050586525587465true")
    }
  }

  "java_string_buffer" in {
    withBench() { bench =>
      bench.java_string_buffer() should include("11307240360.655293355-47967987206613007610.5050586525587465true")
    }
  }

  "string_concat" in {
    withBench() { bench =>
      bench.string_concat() should include("11307240360.655293355-47967987206613007610.5050586525587465true")
    }
  }

  "plus_operator" in {
    withBench() { bench =>
      bench.plus_operator() should include("11307240360.655293355-47967987206613007610.5050586525587465true")
    }
  }

  "template" in {
    withBench() { bench =>
      bench.template() should include("11307240360.655293355-47967987206613007610.5050586525587465true")
    }
  }
}
