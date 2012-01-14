package org.kiaulens.examples.scalalib
import scala.util.parsing.combinator._

/**
 * Parser-Kombinatoren-Bibliothek in Scala
 */
object ParserKombinatorenMain {
  def main (args: Array[String]) {
    
    // Beispiel: Parsen folgendes Eingabe-Strings
    val input = """Mayr, Hubertus (123456): 90 Punkte = 1,0
                  |Maler, Brigitte (876543): 86 Punkte = 1,3
                  |Reimann, Gundolf (471112): 1 Punkt = 5,0""".stripMargin
    
    // 5 Kombinatoren:
      // 'a ~ b'  - erzeugt einen neuen Parser, der zuerst mit a, dann mit b parst und das Gesamtergebnis zurückgibt
      // 'a <~ b' - parst wie 'a ~ b', gibt aber nur das Ergebnis des Parsers 'a' zurück
      // 'a >~ b' - gibt analog nur das Ergebnis von 'b' zurück
      // 'P | Q'  - wenn Parser P erfolgreich war liefert er das Ergebnis, sonst Parser Q
      // 'opt(P)' - ist Parser 'P' erfolgreich und hat als Ergebnis 'x' liefert 'opt(P)'
      //            den Wert 'Some(x)' zurück, sonst 'None' (Parsing-Vorgang bricht nicht ab)
    
    // ein Parser hat den Typ 'Parser[T]', wobei T der Ergebnistyp des Parsers ist, der zurückgegeben wird
    val eps = new ExamParser
    println(eps.parseAll(eps.exam,input).get)
    
    
  }
}

class Student(val name: String, val matNr: Int) {
  override def toString = name+" ("+matNr+")"
}

class ExamParser extends RegexParsers {
  val int: Parser[Int] = """\d+""".r ^^ (_.toInt)
  val studentname: Parser[String] = """[^(]+""".r ^^ (_.trim)
  val matNr: Parser[Int] = "("~>int<~"): "
  val points: Parser[Int] = int<~"""\W*Punkte?\W*=\W*""".r
  val mark: Parser[Float] = """[123],[037]|4,[03]|5,0""".r ^^ (_.replace(',','.').toFloat)
  val oneExam: Parser[(Student,Int,Float)] = studentname~matNr~points~mark ^^ {
    case name~nr~points~mark => (new Student(name,nr),points,mark)
  }
  val exam: Parser[List[(Student,Int,Float)]] = repsep(oneExam,"""\W*""".r)
}