package co.datadome.pub.scalabenchmarks.jvms.misc.knapsack

final case class Item(value: Int, weight: Int) {
  lazy val ratio: Double = value.toDouble / weight
}