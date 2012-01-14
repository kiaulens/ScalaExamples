package org.kiaulens.examples.fp

/**
 * Fehlerbehandlung
 * 
 * Ein Programmblock in dem mit einem Fehler zu rechnen ist wird mit 'try' umschlossen.
 * Im anschließenden 'catch'-Block werden die geworfenen Exceptions behandelt.
 * Im gegensatz zu anderen Programmiersprachen wie z.B. Java, welche mehrere 'catch'-Blöcke
 * für geworfene Exceptions vorsehen, werden diese in Scala in einem einzigen Block
 * mittels 'pattern matching' unterschieden.
 * 
 * Mit 'finally' nach dem 'catch'-Block wird wie in Java ein Bereich definiert, in dem
 * alle Befehle ausgeführt werden, egal ob ein Fehler auftrat oder nicht.
 * 
 */
object ExceptionHandlingMain {
  def main(args: Array[String]) {
    
    def readIntOption() = {
      try {
        Some(readInt)
      } catch {
        case _: NumberFormatException => None
        case _ => throw ReadIntException
      } finally {
        println("readIntOption beendet.")
      }
    }
    print("Geben Sie eine Zahl ein: ")
    println("Ihre Zahl: "+readIntOption)
    
    // Scala unterscheidet nicht zwischen 'checked' und 'unchecked' Exceptions
    // wird allerdings eine Scala-Methode aus Java aufgerufen, führt dies zu einem
    // Fehler, daher wird als Ausweg die '@throws'-Annotation verwendet
    class Reader(fname: String) {
      private val in = new java.io.BufferedReader(new java.io.FileReader(fname))
      @throws(classOf[java.io.IOException]) // unnötig innerhalb von Scala
      def read() = in.read()
    }
    
    
    
  }
}

case object ReadIntException extends Exception("unexpected error in readIntOption")