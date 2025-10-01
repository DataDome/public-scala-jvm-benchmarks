package co.datadome.pub.scalabenchmarks.jvms.misc.nqueens

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/** The N-Queens problem is a classic combinatorial problem to place N chess queens on an N×N chessboard in such a way that no two queens can attack each other.
 * This problem is computationally intensive, and its duration is directly proportional to the chessboard size
 * (i.e., the larger the chessboard, the more time it takes to compute the solutions).
 * The benchmark utilizes two alternative approaches to solve the N-Queens problem:
 * - Backtracking
 * - Simulated Annealing
 *
 * Simulated Annealing is a probabilistic optimization algorithm inspired by the annealing process in metallurgy.
 * It is used for solving combinatorial optimization problems, where the goal is to find the best solution among a large set of possible solutions.
 * It starts with an initial solution and iteratively explores neighboring solutions, gradually reducing the "temperature"
 * (a parameter that controls the probability of accepting worse solutions).
 *
 * Backtracking is an algorithmic technique used to systematically explore all possible solutions to a problem by incrementally building
 * a solution and undoing incorrect choices if they lead to dead-ends. It explores all possible paths in the solution space,
 * pruning those that violate problem constraints, to find a feasible or optimal solution.
 *
 * Note: Simulated Annealing does not guarantee an optimal solution, unlike Backtracking, which guarantees to find the exact solution if it exists.
 *
 * The performance of Simulated Annealing versus Backtracking can vary significantly based on the choice of parameters, cooling schedule, N-size, etc. Example:
 * - for a smaller N (e.g., N=8; i.e., a typical 8x8 chessboard size), Backtracking is capable of finding all possible solutions relatively quickly.
 * - for a larger N (e.g., N=64, i.e., 64x64 chessboard size), the search space becomes astronomically large, making it less practical for Backtracking to explore all configurations.
 * In such cases, Simulated Annealing may become more practical, as it can efficiently explore the search space but without guaranteeing an optimal solution.
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class NQueensBenchmark {

  private given random: Random = new Random(16384)

  @Param(Array("24"))
  var n: Int = uninitialized

  @Setup
  def setup(): Unit = {
  }

  @Benchmark
  def backtracking(): Array[Array[Boolean]] = {
    NQueensBacktracking.placeNQueens(n)
  }

  @Benchmark
  def simulated_annealing(): Array[Array[Boolean]] = {
    NQueensSimulatedAnnealing.placeNQueens(n)
  }
}
