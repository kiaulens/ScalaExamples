package org.kiaulens.examples.fp

/**
 * Rekursion versteht, wer Rekursion versteht
 * 
 * Imperative Lösungsansätze wiedersprechen dem funktionalen Prinzip, da Werte der gleichen
 * Variablen in einer Schleife mehrfach geändert werden müssen. Daher werden rekursive
 * Ansätze bei der funktionalen Programmierung zur Lösung von Aufgaben, welche Schleifen
 * benötigen, verwendet.
 */
object RecursionMain {
  def main(args: Array[String]) {
    
    // Aufsummierung aller Elemente einer Integer-Liste
    // imperative Lösung
    def impSum(list: List[Int]) = {
      var sum = 0
      for (elem <- list) sum += elem
      sum
    }
    
    // rekursive Lösung ('nicht-endrekursiv')
    def rekSum(list: List[Int]): Int = {
      if (list.isEmpty) 0
      else list.head + rekSum(list.tail)
    }
    
    // (Compiler optimierbare) rekursive Lösung ('endrekursiv')
    def optSum(list: List[Int]) = {
      def sumHelper(x: Int, list: List[Int]): Int = {
        if (list.isEmpty) x
        else sumHelper(x+list.head, list.tail)
      }
      sumHelper(0,list)
    }
    
    // Berechnung ob eine Zahl gerade oder ungerade ist
    // wechselseitige Rekursion
    def isEven(n: Int): Boolean = if (n==0) true else isOdd(n-1)
    def isOdd(n: Int): Boolean = if (n==0) false else isEven(n-1)
    
    // wechselseitige Rekursion mit 'Trampoline'-Ansatz
    import scala.util.control.TailCalls._
    def isEvenT(n: Int): TailRec[Boolean] = if (n==0) done(true) else tailcall(isOddT(n-1))
    def isOddT(n: Int): TailRec[Boolean] = if (n==0) done(false) else tailcall(isEvenT(n-1))
    println(isEvenT(100000).result)
    
  }
}