package org.kiaulens.examples.fp

object OptionCatchMain {
  def main(args: Array[String]) {
    // Mechanismus um Funktionen erst in der Kontrollstruktur auszuführen
    def optionCatch(f: => Any) = {
      try {
        Some(f)
      } catch {
        case _ => None
      }
    }
    // Parameterübergabe per 'Call-by-Name' (wird nicht sofort ausgewertet wie bei 'Call-by-Value')
    optionCatch {
      print("Numerator? ")
      val n = readInt
      print("Denominator? ")
      val d = readInt
      n/d
    } match {
      case Some(x)	=> println("Result is "+x)
      case None		=> println("An error occured")
    }
  }
}