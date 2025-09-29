package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import co.datadome.pub.scalabenchmarks.jvms.misc.wordfrequency.WordFrequencyBenchmark

class CatsEffectWordFrequencyBenchmarkSpec extends TestSuite {

  private def withBench[A](f: CatsEffectWordFrequencyBenchmark => A): A = {
    val bench = new CatsEffectWordFrequencyBenchmark()
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.wordFrequency()
    }
  }

  "wordFrequency" in {
    withBench { bench =>
      val result = bench.wordFrequency()
      result.size should be(8887)
      result.toSeq.sortBy(_._2).reverse.take(5) should be(Seq("the" -> 5426, "I" -> 3038, "and" -> 2887, "to" -> 2788, "of" -> 2734))

      val otherBench = new WordFrequencyBenchmark
      result should contain theSameElementsAs(otherBench.iterative())
    }
  }

  "wordFrequencyStream" in {
    withBench { bench =>
      val result = bench.wordFrequencyStreamFs2()
      result.size should be(8887)
      result.toSeq.sortBy(_._2).reverse.take(5) should be(Seq("the" -> 5426, "I" -> 3038, "and" -> 2887, "to" -> 2788, "of" -> 2734))

      val otherBench = new WordFrequencyBenchmark
      result should contain theSameElementsAs (otherBench.iterative())
    }
  }

}
