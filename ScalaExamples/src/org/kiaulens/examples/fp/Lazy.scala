package org.kiaulens.examples.fp

/**
 * "eager evaluation"
 * Alle Felder einer Klasse werden beim Erzeugen des Objektes berechnet (standard).
 * 
 * "lazy evaluation"
 * Der Wert eines Feldes wird beim Zugriff auf dieses berechnet.
 * Dieser berechnete Wert wird dann aber im Gegensatz zu einer Methode gespeichert.
 * Modifier 'lazy' (nur zulässig für 'val's)
 * 
 * 'lazy val x' macht nur Sinn, wenn alle 'val's die in ihrer Berechnung auf 'x' zugreifen
 * auch lazy sind. 'var's, die nicht lazy sein können, sollten für ihre Berechnung
 * nicht x benötigen oder 'x' sollte nicht lazy sein.
 */
object LazyMain {
  def main(args: Array[String]) {
    val n = new PositiveNumber() {
      val inValue = 12
    }
    println(n.value)
  }
}

trait PositiveNumber {
  val inValue: Int
  lazy val value = {
    require(inValue>0)
    inValue
  }
}