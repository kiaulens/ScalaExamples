package org.kiaulens.examples.oop

/**
 * Beispiel eines "Companion-Objektes" (äquivalent zu 'static' in Java)
 */
object SharedCounterMain {
  def main(args: Array[String]) {
    val sc1, sc2 = new SharedCounter
    for(i <- 1 to 10) {
      sc1.count
      println(sc1)
      sc2.count
      println(sc2)
    }
  }
}

object SharedCounter { // Das "Companion-Objekt" muss sich in der selben Quelltextdatei befinden, wie die gleichnamige Klasse
  private[this] var count = 0
  private def value = count // "parameterless method", Konvention: nur lesender Zugriff
  private def increment() { // "empty-paren method", Konvention: schreibender Zugriff und I/O
    count += 1
  }
}

class SharedCounter {
  private[this] var counted = 0
  def count() {
    SharedCounter.increment()
    counted += 1
  }
  override def toString =
    "Current shared value is "+SharedCounter.value+
    ". Incremented shared counter "+counted+" times."
}