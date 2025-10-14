package co.datadome.pub.scalabenchmarks.jvms.misc.wordfrequency

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class WordFrequencyBenchmarkSpec extends TestSuite {

  private def withBench[A](f: WordFrequencyBenchmark => A): A = {
    val bench = new WordFrequencyBenchmark
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      bench.setup()
      // Call at least one method to ensure no exceptions on simple path
      bench.iterative()
    }
  }

  "iterative" in {
    withBench { bench =>
      val result = bench.iterative()
      result.size should be(8887)
      result.toSeq.sortBy(_._2).reverse.take(5) should be(Seq("the" -> 5426, "I" -> 3038, "and" -> 2887, "to" -> 2788, "of" -> 2734))
    }
  }

  "functional" in {
    withBench { bench =>
      val result = bench.functional()
      result.size should be(8887)
      result.toSeq.sortBy(_._2).reverse.take(5) should be(Seq("the" -> 5426, "I" -> 3038, "and" -> 2887, "to" -> 2788, "of" -> 2734))
    }
  }

  "dac" in {
    withBench { bench =>
      val result = bench.dac()
      result.size should be(8887)
      result.toSeq.sortBy(_._2).reverse.take(5) should be(Seq("the" -> 5426, "I" -> 3038, "and" -> 2887, "to" -> 2788, "of" -> 2734))
    }
  }
}
