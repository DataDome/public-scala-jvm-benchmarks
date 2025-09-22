package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import zio.ZIO

object ParallelVariance {

  val dac = new DivideAndConquer(1_000_000)

  def variance(ages: Vector[Double]): ZIO[Any, Nothing, Double] = for {
    total <- dac.window(0, ages.size)(computeTotal(ages, _, _))(_ + _)
    average = total / ages.size
    varianceSum <- dac.window(0, ages.size)(computeVariance(ages, average, _, _))(_ + _)
  } yield varianceSum / ages.size

  private def computeTotal(ages: Vector[Double], start: Int, end: Int): ZIO[Any, Nothing, Double] = ZIO.succeed {
    ages.slice(start, end).sum
  }

  private def computeVariance(ages: Vector[Double], average: Double, start: Int, end: Int): ZIO[Any, Nothing, Double] = ZIO.succeed {
    ages.slice(start, end).map(a => (a - average) * (a - average)).sum
  }

}
