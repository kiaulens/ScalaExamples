package org.kiaulens.examples.oop

/**
 * Konstruktoren dienen zur Initialisierung von Feldern und
 * Ausführen von Operationen, die nur beim ersten Erzeugen eines Objektes benötigt werden.
 */
object ConstructorMain {
  def main(args: Array[String]) {
    val say = new Constructor("Hello World")
    println(say.greeting)
    say.hello()
    say.greeting = "Hallo Sebastian"
    say.hello()
    val sayDef = new Constructor
    sayDef.hello()
  }
}

class Constructor(var greeting: String = "Default-Wert im Konstruktor") {
  // alles was der primäre Konstruktor noch tun soll, wird unmittelbar in die Klasse geschrieben
  require(!greeting.isEmpty()) // Überprüfung des Klassen-Parameters
  
  // weitere Konstruktoren werden als 'auxiliary constructors' bezeichnet
  def this() = this("Hello")
  
  def hello() {
    println(greeting)
  }
}