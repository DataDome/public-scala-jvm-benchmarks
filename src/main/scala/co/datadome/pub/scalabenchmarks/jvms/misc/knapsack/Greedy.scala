package co.datadome.pub.scalabenchmarks.jvms.misc.knapsack

import scala.collection.mutable.ListBuffer
import co.datadome.pub.scalabenchmarks.jvms.utils.*


object Greedy {

  given Ordering[Item] = (o1: Item, o2: Item) => java.lang.Double.compare(o1.ratio, o2.ratio)

  def knapsack(capacity: Int, items: Array[Item]): ListBuffer[Item] = {
    val sortedItems = items.sorted

    val selectedItems = ListBuffer[Item]()
    var currentWeight = 0

    sortedItems.fastReverseForeach { item =>
      if (currentWeight + item.weight <= capacity) {
        selectedItems.append(item)
        currentWeight += item.weight
      }
    }
    selectedItems
  }
}
