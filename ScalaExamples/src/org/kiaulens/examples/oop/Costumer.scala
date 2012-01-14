package org.kiaulens.examples.oop

object CostumerMain {
  def main(args: Array[String]) {
    
  }
}

/**
 * Beispiel: Klassen- und Objekt-Vverschachtelung
 */
class Costumer {
  var costumerId: Int = _
  object Address {
    var street: String = _
    var postcode: String = _
    var city: String = _
    var country: String = _
  }
}