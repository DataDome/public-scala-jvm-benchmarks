package co.datadome.pub.scalabenchmarks.jvms.libs.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized


/**
 * Benchmark for jsoniter-scala library. Testing serialization and deserialization performance.
 *
 * This benchmark measures the performance of jsoniter-scala, a high-performance JSON library
 * for Scala that uses compile-time code generation via macros to achieve zero-overhead parsing.
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class JsoniterBenchmark {

  // Jsoniter codecs - generated at compile time
  given JsonValueCodec[Person] = JsonCodecMaker.make
  given JsonValueCodec[List[Person]] = JsonCodecMaker.make

  @Param(Array("1000"))
  var size: Int = uninitialized

  private var people: List[Person] = uninitialized
  private var jsonBytes: Array[Byte] = uninitialized
  private var jsonString: String = uninitialized

  @Setup
  def setup(): Unit = {
    people = (1 to size).map { i =>
      Person(
        id = i,
        firstName = s"FirstName$i",
        lastName = s"LastName$i",
        email = s"user$i@example.com",
        age = 20 + (i % 50),
        isActive = i % 2 == 0,
        balance = 1000.0 + (i * 10.5),
        address = Address(
          street = s"$i Main Street",
          city = s"City${i % 100}",
          state = s"ST${i % 50}",
          zipCode = f"${10000 + (i % 90000)}%05d"
        ),
        tags = List(s"tag${i % 10}", s"tag${i % 20}", s"tag${i % 30}")
      )
    }.toList

    jsonBytes = writeToArray(people)
    jsonString = writeToString(people)
  }

  @Benchmark
  def serializeToBytes(): Array[Byte] = {
    writeToArray(people)
  }

  @Benchmark
  def serializeToString(): String = {
    writeToString(people)
  }

  @Benchmark
  def deserializeFromBytes(): List[Person] = {
    readFromArray[List[Person]](jsonBytes)
  }

  @Benchmark
  def deserializeFromString(): List[Person] = {
    readFromString[List[Person]](jsonString)
  }
}
