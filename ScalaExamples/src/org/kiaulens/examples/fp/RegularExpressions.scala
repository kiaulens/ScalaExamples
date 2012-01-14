package org.kiaulens.examples.fp
import scala.util.matching.Regex

/**
 * Reguläre Ausdrücke
 * 
 */
object RegularExpressionsMain {
  def main(args: Array[String]) {
    
    def extractDate(str: String): Option[(Int,Int,Int)] = {
      val dateRegex = """(\d{1,2})\.(\d{1,2}).(\d{2}|\d{4})""".r
      str match {
        case dateRegex(d,m,y)	=> Some((d.toInt,m.toInt,y.toInt))
        case _					=> None
      }
    }
    
    println(extractDate("05.03.1986"))
    
  }
}