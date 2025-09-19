package co.datadome.pub.scalabenchmarks.jvms.misc.knapsack

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

import scala.collection.mutable

class KnapsackBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 50000)(f: KnapsackBenchmark => A): A = {
    val bench = new KnapsackBenchmark
    bench.itemsCount = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      bench.setup()
      // Call at least one method to ensure no exceptions on simple path
      bench.greedy()
    }
  }

  "greedy" in {
    withBench() { bench =>
      val result = bench.greedy()
      check(bench, result)
    }
  }

  "dynamic" in {
    withBench(5000) { bench =>
      val result = bench.dynamic()
      check(bench, result)
      result.map(_.value).sum should be (44190)
    }
  }

  def check(bench: KnapsackBenchmark, selectedItems: mutable.ListBuffer[Item]): Unit = {
    val totalWeight = selectedItems.map(_.weight).sum
    val capacityDifference = bench.KnapsackCapacity - totalWeight
    totalWeight should be <= bench.KnapsackCapacity
    capacityDifference should be <= bench.MaxItemWeight
    ()
  }
}
