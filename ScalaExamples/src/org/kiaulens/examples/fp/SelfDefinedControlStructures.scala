package org.kiaulens.examples.fp

object SelfDefinedControlStructuresMain {
  def main(args: Array[String]) {
    // Parameterlisten mit genau einem Parameter können mit geschweiften Klammern umschlossen werden
    // z.B. addTuple { (1,2) } { (3,5) }
    
    def printTupleAddedTo(t1: (Int, Int))(t2: (Int, Int)) =
      println(t1+" + "+t2+" = "+(t1._1 + t2._1, t1._2 + t2._2))
    // Nutzung der Funktion 'printTupleAddedTo' als Kontrollstruktur
    printTupleAddedTo(1,2) {
      print("Geben Sie eine erste Komponente ein: ")
      val x = readInt
      print("Geben Sie eine zweite Komponente ein: ")
      val y = readInt
      (x,y)
    } // durch die Nutzung geschweifter Klammern ist es möglich, den Parameter als Codeblock zu übergeben
    
    // Kontrollstrukturen mit Funktionen als Parameter
    def guessNumber(predicate: Int => Boolean)(number: Int) =
      if(predicate(number)) println("richtig") else println("falsch")
      
    guessNumber(_==5) {
      print("Welche zahl suchen wir? ")
      readInt
    }
  }
}