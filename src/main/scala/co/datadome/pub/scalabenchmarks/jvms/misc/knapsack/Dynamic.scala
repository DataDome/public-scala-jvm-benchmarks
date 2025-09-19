package co.datadome.pub.scalabenchmarks.jvms.misc.knapsack

import co.datadome.pub.scalabenchmarks.jvms.utils.*

import scala.collection.mutable

/** For this algorithm, see: https://en.wikipedia.org/wiki/Knapsack_problem#0-1_knapsack_problem
 * Note that the wikipedia formulas use a 1-based index, instead of a zero-based index.
 */
object Dynamic {

  def knapsack(capacity: Int, items: Array[Item]): mutable.ListBuffer[Item] = {

    val itemsCount: Int = items.length

    val storage: Array[Array[Int]] = Array.fill(itemsCount + 1, capacity + 1)(0)

    /* First to determine for each item-limit and weight-limit, the maximum available value */
    fastLoop(1, itemsCount + 1) { i => // i is the index limit, excluded (so if i = 2, only the items with index 0 and 1 can be used)
      val currentItem: Item = items(i - 1)
      fastLoop(0, capacity + 1) { w => // w is the total weight limit
        if (currentItem.weight > w) {
          // can't add it, so for this index-limit and this weight-limit, the result is the same as we had without that item
          storage(i)(w) = storage(i - 1)(w)
        } else {
          val storageWithoutItem = storage(i - 1)(w)
          val storageWithForcedItem = storage(i - 1)(w - currentItem.weight) + currentItem.value
          storage(i)(w) = Math.max(storageWithoutItem, storageWithForcedItem)
        }
      }
    }

    /* Then, extract the actual list of items */
    val selectedItems: mutable.ListBuffer[Item] = mutable.ListBuffer[Item]()
    var w: Int = capacity
    var i: Int = itemsCount
    while (i > 0 && w > 0) {
      if (storage(i)(w) != storage(i - 1)(w)) {
        val selectedItem: Item = items(i - 1)
        selectedItems.append(selectedItem)
        w -= selectedItem.weight
      }
      i -= 1
    }

    selectedItems
  }

}
