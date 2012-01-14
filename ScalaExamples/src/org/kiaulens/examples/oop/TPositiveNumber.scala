package org.kiaulens.examples.oop

/**
 * Beispiel: vorinitialisierte Felder
 */
object PositiveNumberMain {
  def main(args: Array[String]) {
    /*val n = new TPositiveNumber { // liefert eine Exception, da zuerst der Standardwert für Int gegen require geprüft wird
      val value = 12
    }*/
    // Lösung: 'vorinitialisierte Felder' (siehe u.) oder 'lazy' vals
    val n = new {
      val value = 12
    } with TPositiveNumber
  }
}

trait TPositiveNumber {
  val value: Int
  require(value>0)
}