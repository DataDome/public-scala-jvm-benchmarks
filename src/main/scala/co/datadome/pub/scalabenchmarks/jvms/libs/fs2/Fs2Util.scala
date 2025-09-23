package co.datadome.pub.scalabenchmarks.jvms.libs.fs2

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.Stream

object Fs2Util {

  def run[A](stream: Stream[IO, A]): Seq[A] = {
    stream.compile.toVector.unsafeRunSync()
  }

}
