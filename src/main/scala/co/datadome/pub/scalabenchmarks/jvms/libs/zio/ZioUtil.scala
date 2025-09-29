package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import zio.stream.ZStream
import zio.{Scope as _, *}

object ZioUtil {

  def run[A](za: ZIO[Any, Throwable, A]): A = {
    Unsafe.unsafe { implicit unsafe =>
      Runtime.default.unsafe.run(za) match {
        case Exit.Failure(cause) => throw cause.squash
        case Exit.Success(a) => a
      }
    }
  }

  def run[A](zs: ZStream[Any, Throwable, A]): Chunk[A] = {
    Unsafe.unsafe { implicit unsafe =>
      val za: ZIO[Any, Throwable, Chunk[A]] = zs.runCollect

      Runtime.default.unsafe.run(za) match {
        case Exit.Failure(cause) => throw cause.squash
        case Exit.Success(a) => a
      }
    }
  }

}
