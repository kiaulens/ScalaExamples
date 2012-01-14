package org.kiaulens.examples.oop

/**
 * Beispiel für Transparenten Zugriff. 'dollar' sieht von außen aus, wie ein Feld,
 * wird aber in der Klasse als Methode definiert.
 */
object CurrencyMain {
  def main(args: Array[String]) {
    val m1, m2 = new Currency
    println(m1)
    println(m2)
    m1.euro = 20
    println(m1)
    m2.dollar = 20
    println(m2)
    Currency.exchangeRate = 4.0f
    println(m1)
    println(m2)
  }
}

object Currency { // "Companion-Objekt", zum Speichern gemeinsamer Felder, die in allen Objekten einer Klasse gleich sind
  var exchangeRate = 2.0f
}

class Currency {
  var euro = 0.0f // oder "var euro: Float = _" weist den Standardwert (0) zu
  // Standardwerte sind für Numerische Datentypen '0', für Boolean 'false', für Unit '()' und 'null' für Referenztypen
  def dollar = euro * Currency.exchangeRate
  def dollar_= (m: Float) {
    euro = m / Currency.exchangeRate
  }
  override def toString = euro+" EUR / "+dollar+" USD"
}