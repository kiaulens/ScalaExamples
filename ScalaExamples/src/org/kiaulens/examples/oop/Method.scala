package org.kiaulens.examples.oop

/**
 * Scala unterscheidet nicht zwischen Methoden und Operatoren.
 */
object MethodMain {
  def main(args: Array[String]) {
    // leere Klammern können im Grunde immer weggelassen werden
    // z.B. Ausgabe einer neuen Zeile:
    println // dies sollte aber auf Grund des Nebeneffektes der Methode vermieden werden
    
    // Operatoren sind auch nur Methoden:
    // Beispiele:
    1 + 2 // ist die Methode + des Objektes 1, also
    (1).+(2) // (die Klammern um 1 sind hierbei nötig, da der Kompiler sont 1. als Gleitpunktzahl interpretiert)
    
    "Hello World" substring 6 // beliebeige Methoden können als Infix-Operatoren geschrieben werden
    "Hello World" substring (6,7) // bei mehreren Argumenten sind die Klammern beizubehalten
    
    "Hallo" toUpperCase // Methoden ohne Argumente können in Postfix-Notation geschrieben werden
    
    // in Präfix dürfen ausschließlich die Operatoren +, -, ! und ~ geschrieben werden (unäre Operatoren)
    // der zugehörige Methoden-Name für unary-Operatoren ist unary_<op>, z.B.:
    -3 // entspricht
    (3).unary_-
    
    // Arrays sind normale Objekte
    val array = new Array[String](2)
    println(array(0)+", "+array(1))
    array.update(0, "World") /* oder */; array(0) = "World"
    array.update(1, "Reader")
    for (i <- 0 to ((1).unary_-).+(array length))
      println("Hello "+array.apply(i)) // oder nur array(i)
    // Regel: Foglen einem Objektnamen Klammern mit einem oder mehreren Werten in der Argumentliste, wird diese
    // mit 'apply' aufgerufen. Es reicht also um diese schreibweise nutzen zu können, die Methode 'apply' zu implementieren.
    // Analog gilt dies für Zuweiseungen und 'update'.
  }
}