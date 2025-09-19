package co.datadome.pub.scalabenchmarks.jvms.misc.dijkstra

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/**
 * Find the shortest path between a random source node and all other nodes in a dense graph using Dijkstra's algorithm.
 * The benchmark uses two alternative approaches, each using different data structures to represent the graph, as follows:
 * - an adjacency matrix
 * - an adjacency list with a binary heap (min heap)
 *
 * Adjacency Matrix: This implementation uses an adjacency matrix representation of the graph combined with a standard priority queue (binary heap).
 * The adjacency matrix represents the graph as a 2D array, where the value in the matrix indicates the weight of the edge between two nodes.
 * This approach is suitable for dense graphs but can be inefficient for sparse graphs due to its space usage.
 * The time complexity is O(V^2) due to the cost of accessing the matrix for each node during relaxation.
 *
 * Adjacency List with Binary Heap (Min Heap): This implementation uses a standard binary heap (min heap) combined with an adjacency list representation of the graph.
 * The binary heap is implemented manually using a list of queues (buckets).
 * It can offer better performance than binary heaps for dense graphs with small and non-negative edge weights.
 * Its time complexity is O((V + E) log V).
 *
 * Note: these implementations offer different trade-offs in terms of time complexity, space efficiency, and performance characteristics.
 * The best choice of implementation depends on the specific graph structure, the distribution of edge weights, etc.
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class DijkstraBenchmark {
  // Recommended command line options:
  // - JVM options: -Xms4g -Xmx4g

  private val MaxDistance = 64
  private val random = new Random(16384)

  @Param(Array("8192")) var size: Int = uninitialized
  
  private var sourceNode = 0
  private var adjacencyMatrix: AdjacencyMatrix = uninitialized
  private var adjacencyLists: AdjacencyLists = uninitialized

  @Setup
  def setup(): Unit = {
    sourceNode = random.nextInt(size)
    val seed = random.nextInt()
    adjacencyMatrix = new AdjacencyMatrix(size, MaxDistance, seed)
    adjacencyLists = new AdjacencyLists(size, MaxDistance, seed)
  }

  @Benchmark 
  def adjacency_matrix(): Array[Int] = adjacencyMatrix.dijkstra(sourceNode)

  @Benchmark 
  def adjacency_lists(): Array[Int] = adjacencyLists.dijkstra(sourceNode)

}
