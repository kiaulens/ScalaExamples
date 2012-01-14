package org.kiaulens.examples.oop

/**
 * Beispiel: Vererbung
 */
object InheritanceMain {
  def main(args: Array[String]) {
    val say = new Inheritance()
    say.hello()
    say.goodby()
    
    val sayG: SuperHello = new Inheritance("Hi","cu")
    sayG.hello()
    // sayG.goodby() // funktioniert nicht.
    val sayT: SuperHello = new HelloTwice("Salut")
    sayT.hello() // Polymorphie und dynamisches Binden
  }
}
object SuperHello {
  val defaultGreeting = "Hello"
}
class SuperHello(val greeting: String = SuperHello.defaultGreeting) {
  def hello() {
    println(greeting)
  }
}
/**
 * Abgeleitete Klasse von Hello.
 * Der Klassen-Parameter 'greeting' wird an den Konstruktor der Basisklasse 'SuperHello' übergeben.
 * Dies entspricht dem 'super'-Aufruf in Java. 'super' dient in Scala dem Aufruf einer Methode der
 * Basisklasse.
 * Das überschreiben des Klassen-Parameters kann in der Superklasse mit 'final' verhindert werden.
 */
class Inheritance(override val greeting: String = "Hi", farewell: String = "Goodby") extends SuperHello { 
  def goodby() = println(farewell)
}

class HelloTwice(greeting: String) extends SuperHello(greeting) {
  override def hello() {
    println(greeting+", "+greeting)
  }
}