package co.datadome.pub.scalabenchmarks.jvms.libs.jsoniter

final case class Address(
  street: String,
  city: String,
  state: String,
  zipCode: String
)

final case class Person(
  id: Int,
  firstName: String,
  lastName: String,
  email: String,
  age: Int,
  isActive: Boolean,
  balance: Double,
  address: Address,
  tags: List[String]
)
