package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import zio.ZIO

final class DivideAndConquer(val threshold: Int) {

  def window[A](start: Int, end: Int)(compute: (Int, Int) => ZIO[Any, Nothing, A])(reduce: (A, A) => A): ZIO[Any, Nothing, A] = {
    val length = end - start
    if (length < threshold) compute(start, end)
    else {
      val middle = start + length / 2
      val left = window(start, middle)(compute)(reduce)
      val right = window(middle, end)(compute)(reduce)
      (left zipPar right).map((a, b) => reduce(a, b))
    }
  }

}
