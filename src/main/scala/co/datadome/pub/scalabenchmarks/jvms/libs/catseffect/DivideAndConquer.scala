package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.effect.IO
import cats.syntax.all.*

final class DivideAndConquer(val threshold: Int) {

  def window[A](start: Int, end: Int)(compute: (Int, Int) => IO[A])(reduce: (A, A) => A): IO[A] = {
    val length = end - start
    if (length < threshold) compute(start, end)
    else {
      val middle = start + length / 2
      val left = window(start, middle)(compute)(reduce)
      val right = window(middle, end)(compute)(reduce)
      (left, right).parMapN(reduce)
    }
  }

}
