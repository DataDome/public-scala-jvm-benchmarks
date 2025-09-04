package co.datadome.pub.scalabenchmarks.jvms.api

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.boundary.break
import scala.util.{Random, boundary}


/** Just a model of a Scala benchmark */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class EnumLookupBenchmark {

  import EnumLookupBenchmark.*

  private val random = new Random(16384)

  private val enumValues: Array[Car] = Car.cachedValues
  private var lookUpValue: String = uninitialized

  @Setup
  def setup(): Unit = {
    lookUpValue = enumValues(random.nextInt(enumValues.length)).label;
  }

  @Benchmark
  def cached_enum_values(): Car = {
    Car.fromCachedValues(lookUpValue);
  }

  @Benchmark
  def enum_values(): Car = {
    Car.fromValues(lookUpValue);
  }
}

object EnumLookupBenchmark {

  enum Car(val label: String) {
    case Acura extends Car("Acura")
    case Alfa_romeo extends Car("Alfa Romeo")
    case Aston_martin extends Car("Aston Martin")
    case Audi extends Car("Audi")
    case Bentley extends Car("Bentley")
    case Bmw extends Car("BMW")
    case Bugatti extends Car("Bugatti")
    case Cadillac extends Car("Cadillac")
    case Chevrolet extends Car("Chevrolet")
    case Chrysler extends Car("Chrysler")
    case Citroen extends Car("Citroen")
    case Dodge extends Car("Dodge")
    case Ferrari extends Car("Ferrari")
    case Fiat extends Car("Fiat")
    case Ford extends Car("Ford")
    case Honda extends Car("Honda")
    case Hyundai extends Car("Hyundai")
    case Infiniti extends Car("Infiniti")
    case Jaguar extends Car("Jaguar")
    case Jeep extends Car("Jeep")
    case Kia extends Car("Kia")
    case Koenigsegg extends Car("Koenigsegg")
    case Lamborghini extends Car("Lamborghini")
    case Land_rover extends Car("Land Rover")
    case Lexus extends Car("Lexus")
    case Maserati extends Car("Maserati")
    case Mazda extends Car("Mazda")
    case Mercedes_benz extends Car("Mercedes-Benz")
    case Mitsubishi extends Car("Mitsubishi")
    case Nissan extends Car("Nissan")
    case Peugeot extends Car("Peugeot")
    case Porsche extends Car("Porsche")
    case Renault extends Car("Renault")
    case Saab extends Car("Saab")
    case Subaru extends Car("Subaru")
    case Suzuki extends Car("Suzuki")
    case Rolls_royce extends Car("Rolls Royce")
    case Tata_motors extends Car("Tata Motors")
    case Toyota extends Car("Toyota")
    case Volkswagen extends Car("Volkswagen")
    case Volvo extends Car("Volvo")
  }

  object Car {
    val carCount: Int = Car.values.length
    val cachedValues: Array[Car] = Car.values

    def fromValues(target: String): Car = {
      var i = 0
      while (i < carCount) {
        val c = Car.values(i)
        if (c.label.equals(target)) {
          return c
        }
        i += 1
      }
      throw new IllegalArgumentException(s"Unexpected value [$target]");
    }

    def fromCachedValues(target: String): Car = {
      var i = 0
      while (i < carCount) {
        val c = Car.cachedValues(i)
        if (c.label.equals(target)) {
          return c
        }
        i += 1
      }
      throw new IllegalArgumentException(s"Unexpected value [$target]");
    }
  }
}