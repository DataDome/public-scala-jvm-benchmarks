package co.datadome.pub.scalabenchmarks.jvms.libs.ziostreams

import zio.*
import zio.stream.*

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex


object WordFrequency {
  
  private val Parallelism: Int = 100

  private val Pattern: Regex = """[\W]+""".r

  def wordFrequency(fileName: String): ZStream[Any, Throwable, (String, Long)] = {

    val readFile: IO[Throwable, BufferedSource] = ZIO.attempt(Source.fromFile(fileName))
    val closeFile = (x: BufferedSource) => ZIO.attempt(x.close()).orDie

    val words: ZStream[Any, Throwable, String] =
      ZStream.acquireReleaseWith(readFile)(closeFile)
        .flatMap { is => ZStream.fromIterator(is.getLines()) }
        .flatMapPar(Parallelism) { line => ZStream.fromIterable(Pattern.split(line)) }
        .filterNot(_.isBlank)

    words.groupByKey(identity) { (key, streamForKey) =>
      val counts: ZIO[Any, Throwable, (String, Long)] = streamForKey.runCount.map(key -> _)
      ZStream.fromZIO(counts)
    }
  }

}
