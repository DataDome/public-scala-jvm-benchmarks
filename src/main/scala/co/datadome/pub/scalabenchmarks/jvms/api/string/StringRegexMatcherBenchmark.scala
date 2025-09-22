package co.datadome.pub.scalabenchmarks.jvms.api.string

import org.openjdk.jmh.annotations.*

import java.io.{BufferedReader, FileInputStream, InputStreamReader}
import java.nio.charset.StandardCharsets.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
import scala.util.matching.Regex

/**
 * Measures the performance of matching patterns using Scala's specific tools and syntax.
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class StringRegexMatcherBenchmark {

  private val Space: String = "\\s+"
  private val CurrentDir: String = System.getProperty("user.dir", ".")
  private val FileName: String = CurrentDir + "/src/main/resources/lorem_ipsum.txt"

  /** Matches only strings with at least two vowels, and captures the characters between the first and the last vowel. */
  @Param(Array("^[^aeuio]*[aeuio](.*)[aeuio][^aeuio]*$"))
  var regexString: String = uninitialized

  private var words: Array[String] = uninitialized
  private var wordsCount: Int = uninitialized
  private var regex: Regex = uninitialized
  private var regexNoCapturingGroup: Regex = uninitialized
  private var pattern: Pattern = uninitialized
  private var patternNoCapturingGroup: Pattern = uninitialized

  @Setup
  def setup(): Unit = {
    val bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FileName), UTF_8))
    try {
      words = bufferedReader.lines().toList.asScala.flatMap(line => line.split(Space)).toArray
    } finally {
      bufferedReader.close()
    }
    wordsCount = words.length
    regex = regexString.r
    regexNoCapturingGroup = regexString.filterNot(Set('(', ')')).r
    pattern = Pattern.compile(regexString)
    patternNoCapturingGroup = Pattern.compile(regexString.filterNot(Set('(', ')')))
  }

  @Benchmark
  def java_regex_match(): Boolean = {
    var res = false
    var i = 0
    while (i < wordsCount) {
      val word = words(i)
      val matches = patternNoCapturingGroup.matcher(word)
      if (matches.matches()) {
        res ||= true
      }
      i += 1
    }
    res
  }

  @Benchmark
  def java_regex_capture(): Int = {
    var count = 0
    var i = 0
    while (i < wordsCount) {
      val word = words(i)
      val matches = pattern.matcher(word)
      if (matches.matches()) {
        val content = matches.group(1)
        if (content.nonEmpty) {
          count += 1
        }
      }
      i += 1
    }
    count
  }

  @Benchmark
  def regex_match(): Boolean = {
    var res = false
    var i = 0
    while (i < wordsCount) {
      val word = words(i)
      word match {
        case regexNoCapturingGroup() => res ||= true
        case _ => // do nothing
      }
      i += 1
    }
    res
  }

  @Benchmark
  def regex_capture(): Int = {
    var count = 0
    var i = 0
    while (i < wordsCount) {
      val word = words(i)
      word match {
        case regex(content) if content.nonEmpty => count += 1
        case _ => // do nothing
      }
      i += 1
    }
    count
  }

}
