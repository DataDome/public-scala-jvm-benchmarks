package co.datadome.pub.scalabenchmarks.jvms.misc.dijkstra

import co.datadome.pub.scalabenchmarks.jvms.utils.*

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class AdjacencyLists(val size: Int, val maxDistance: Int, seed: Int) {

  import AdjacencyLists.*

  private val random = new Random(seed)

  /* Sparse graph, where only 20% of couples are directly connected */
  private val graph: Array[mutable.ArrayBuffer[Node]] =
    Array.tabulate(size) { i =>
      val result = mutable.ArrayBuffer[Node]()
      fastLoop(0, size) { j =>
        if (i != j && random.nextInt(10) == 0) { // 10% chance of having an edge
          val weight = random.nextInt(maxDistance) + 1 // random weight between 1 and maxDistance
          result.append(Node(j, weight)): Unit
        }
      }
      result
    }

  // A pool of (reusable) objects is used to minimize allocations during the benchmark method
  private val objectPool: NodePool = new NodePool

  def dijkstra(source: Int): Array[Int] = {
    val distances: Array[Int] = Array.fill(size)(Int.MaxValue)
    val queue: mutable.PriorityQueue[Node] = new mutable.PriorityQueue[Node]

    distances(source) = 0
    val sourceNode = objectPool.get(source, 0)

    queue.enqueue(sourceNode)
    while (queue.nonEmpty) {
      val current = queue.dequeue()
      val dist = current.distance
      val nodeId = current.id
      // add object back to the pool
      objectPool.add(current)

      if (dist > distances(nodeId)) {
        // Skip if the node is already processed with a shorter distance.
      } else {
        graph(nodeId).fastForeach { neighbor =>
          val newDistance = dist + neighbor.distance
          if (newDistance < distances(neighbor.id)) {
            distances(neighbor.id) = newDistance
            val neighborNode = objectPool.get(neighbor.id, newDistance)
            queue.enqueue(neighborNode)
          }
        }
      }
    }
    distances
  }
}


object AdjacencyLists {

  /*= Note: a Node is not a vertex, it's an entry in the adjacency list for a specific vertex */
  private final case class Node(var id: Int, var distance: Int)

  private final class NodePool {
    final private val pool: mutable.Queue[Node] = new mutable.Queue[Node]

    def add(obj: Node): Unit = {
      pool.enqueue(obj)
    }

    def get(id: Int, distance: Int): Node = {
      if (pool.isEmpty) Node(id, distance)
      else {
        val obj = pool.dequeue()
        obj.id = id
        obj.distance = distance
        obj
      }
    }
  }

  private given Ordering[Node] = new Ordering[Node] {
    override def compare(x: Node, y: Node): Int = x.distance - y.distance
  }
}
