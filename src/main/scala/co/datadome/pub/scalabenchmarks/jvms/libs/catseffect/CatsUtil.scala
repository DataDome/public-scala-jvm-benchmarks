package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.Stream

object CatsUtil {
  
  def run[A](ea: IO[A]): A = {
    ea.unsafeRunSync()
  }

  def runFs2[A](stream: Stream[IO, A]): Seq[A] = {
    stream.compile.toVector.unsafeRunSync()
  }

}
