package org.kiaulens.examples.oop

/**
 * Factory-Klassen erzeugen mit 'apply' ein neues Object.
 */
object FactoryMain {
  def main(args: Array[String]) {
    val singleton = Factory
    val say = Factory() // wird zu 'Factory.apply' expandiert
    val say2 = Factory("Hello World")
    say.hello()
    say2.hello()
    singleton().hello() // say() wird transformiert zu say.apply()
  }
}

object Factory {
  // 'apply' gibt ein Factory Objekt zurück
  def apply() = new Factory
  def apply(greeting: String) = new Factory(greeting)
}

class Factory(greeting: String = "Hello") {
  require(!greeting.isEmpty())
  def hello() {
    println(greeting)
  }
}