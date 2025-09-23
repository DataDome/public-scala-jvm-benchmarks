package co.datadome.pub.scalabenchmarks.jvms.libs.ziostreams

import zio.*
import zio.stream.*


object ZioUtil {

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
