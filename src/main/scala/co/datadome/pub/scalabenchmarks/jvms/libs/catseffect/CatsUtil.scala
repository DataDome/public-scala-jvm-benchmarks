package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object CatsUtil {
  
  def run[A](ea: IO[A]): A = {
    ea.unsafeRunSync()
  }

}
