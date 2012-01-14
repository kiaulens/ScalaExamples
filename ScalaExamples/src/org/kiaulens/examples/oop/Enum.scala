package org.kiaulens.examples.oop

/**
 * Der Aufzählungstyp Enumeration.
 */
object Greeting extends Enumeration { // Greeting wird als Aufzählungstyp definiert
  type Greeting = Value
  val Default = Value("Hello")
  val Hi, Howdy = Value
}
import Greeting._

/*object Enum {
  private val acceptableGreetings = List("Hello","Hi","Howdy") // kann übersetzt werden, erzeugt aber zur Laufzeit eine Exception
}*/

class Enum(greeting: Greeting = Default) {
  //require(Enum.acceptableGreetings contains greeting)
  def hello() {
    println(greeting)
  }
}

object EnumMain {
  def main(args: Array[String]) {
    //new Enum("Salve")
    val e = new Enum(Hi)
    e.hello()
  }
}