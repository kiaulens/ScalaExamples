package org.kiaulens.examples.oop

/**
 * Getter- und Setter-Methoden. Überladene Methoden.
 */
object HelloMain {
  def main(args: Array[String]) {
    val say = new Hello
    val sayAgain = new Hello
    
    say.greeting = "Hi" // implizite setter-Methode
    // oder expliziete setter-Methode
    say.greeting_=("Hi")
    // getter-Methode
    println(say.greeting)
    
    say.hello()
    sayAgain.hello()
    println(say) // Aufruf der überschriebenen Methode toString
    println(sayAgain)
    say.hello("Sebastian") // Aufruf der Überladenen Methode 'hello'
  }
}

class Hello {
  type Greeting = String // Typsynonym 'Greeting'
  private[this] var greet: Greeting = "Hello" // nur dieses Objekt kann auf 'greet' zugreifen
  def greeting = greet
  // eine eigene setter-Methode
  def greeting_=(greeting: Greeting) {
    if (this.greeting == greeting)
      println("greeting is already set to \""+this.greeting+"\"")
    else {
      greet = greeting
      println(this)
    }
  }
  def hello() {
    println(greeting)
  }
  // Überladen der Hello-Methode
  def hello(name: String) {
    println(greeting+" "+name)
  }
  override def toString = "Ready to say " + greeting
}