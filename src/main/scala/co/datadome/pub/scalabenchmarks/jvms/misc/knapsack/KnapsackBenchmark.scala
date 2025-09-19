package co.datadome.pub.scalabenchmarks.jvms.misc.knapsack

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.collection.mutable
import scala.compiletime.uninitialized
import scala.util.Random


/*
 * The knapsack problem is a classic optimization problem in computer science and combinatorial optimization.
 * Given a set of items, each with a value and weight, and a knapsack with a maximum weight capacity,
 * the objective is to select items to maximize the total value while ensuring that the sum of their weights does not exceed the knapsack's capacity.
 *
 * The knapsack problem has two main variations:
 *  - 0/1 Knapsack Problem: each item can be either included (0/1 choice) or excluded from the knapsack.
 * An item cannot be partially included. This means you can either take an item entirely or leave it out.
 *  - Fractional Knapsack Problem: there can take fractions of items, allowing to include parts of an item based on its weight.
 *
 * Some important notes regarding the knapsack problem:
 * - The 0/1 Knapsack problem is commonly tackled using dynamic programming, owing to its complex nature.
 * - The Fractional Knapsack problem is typically solved through a greedy algorithm. This is mainly because a 0/1 version of the Fractional Knapsack problem doesn't exist.
 *
 * The benchmark involves several alternative strategies:
 * - Knapsack with Dynamic Programming (for the 0/1 version)
 * - Knapsack with Greedy Programming (for the 0/1 version)
 * - Fractional Knapsack (with Greedy Programming)
 */

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class KnapsackBenchmark {

  private val random = new Random(16384)

  val MaxItemWeight = 32
  val MaxItemValue = 64
  val KnapsackCapacity = 5_000

  @Param(Array("50000"))
  var itemsCount: Int = uninitialized

  var items: Array[Item] = uninitialized


  @Setup
  def setup(): Unit = {
    items = Array.fill(itemsCount)(Item(1 + random.nextInt(MaxItemValue), 1 + random.nextInt(MaxItemWeight)))
  }

  @Benchmark def dynamic(): mutable.ListBuffer[Item] = Dynamic.knapsack(KnapsackCapacity, items)

  @Benchmark def greedy(): mutable.ListBuffer[Item] = Greedy.knapsack(KnapsackCapacity, items)


}
