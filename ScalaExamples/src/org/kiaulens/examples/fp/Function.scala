package org.kiaulens.examples.fp

/**
 * Funktionen können nicht nur als Gleichung, sondern auch als 'Funktionsliteral'
 * definiert werden.
 * 
 * Jede Funktion in Scala ist auch ein Objekt.
 */
object FunctionMain {
  def main(args: Array[String]) {
    
    // Funktionsgleichung
    def incr(i: Int) = i + 1
    
    // Zuweisen des Funktionsliterals nach 'inc'
    val inc = (i: Int) => i + 1
    
    // Anwendung von 'inc'
    println(inc(2))
    
    // Funktionsliteral mit freien Variablen
    var a = 10
    val adder = (i: Int) => i + a
    println(adder(2))
    a = 20
    println(adder(2))
  }
}