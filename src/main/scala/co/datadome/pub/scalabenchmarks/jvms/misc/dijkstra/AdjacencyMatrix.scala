package co.datadome.pub.scalabenchmarks.jvms.misc.dijkstra

import scala.util.Random
import co.datadome.pub.scalabenchmarks.jvms.utils.*

class AdjacencyMatrix(val size: Int, val maxDistance: Int, seed: Int) {

  private val random = new Random(seed)

  private val graph: Array[Array[Int]] = Array.tabulate(size, size) { (i, j) =>
    if (i == j) 0
    else random.nextInt(maxDistance) + 1 // Random weight between 1 and maxDistance
  }

  def dijkstra(source: Int): Array[Int] = {
    val distances: Array[Int] = Array.fill(size)(Int.MaxValue)
    val visited: Array[Boolean] = Array.fill(size)(false)
    
    distances(source) = 0
    fastLoop(0, size - 1) { _ =>
      val minDistNode = findMinDistanceNode(distances, visited)
      visited(minDistNode) = true
      fastLoop(0, size) { j =>
        if (!visited(j) && graph(minDistNode)(j) != 0 && distances(minDistNode) != Int.MaxValue) {
          val newDistance = distances(minDistNode) + graph(minDistNode)(j)
          if (newDistance < distances(j)) {
            distances(j) = newDistance
          }
        }
      }
    }
    distances
  }

  private def findMinDistanceNode(distances: Array[Int], visited: Array[Boolean]): Int = {
    var minDistance = Int.MaxValue
    var minNode = -1
    fastLoop(0, size) { i =>
      if (!visited(i) && distances(i) < minDistance) {
        minDistance = distances(i)
        minNode = i
      }
    }
    minNode
  }

}

