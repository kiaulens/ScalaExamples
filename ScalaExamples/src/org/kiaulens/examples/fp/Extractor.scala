package org.kiaulens.examples.fp

/**
 * Extraktoren
 * 
 * ein Objekt mit einer 'unapply'-Methode
 */
object ExtractorMain {
  def main(args: Array[String]) {
    import Email.email
    implicit def stringToEmail(s: String) = {
      val (headLines,body) = s.lines.toList span (_!="") // Liste von Zeilen wird in zwei Teile zerlegt, mit der leeren Zeile als Trenner
      email { // Aufruf der 'email-withBody' Konstrollstruktur
        (headLines :\ Map[String,String]())( // faltet die Liste der Headerfelder von rechts nach links
          (field,map) =>
            field match {
              case HeaderField(k,v)	=> (map + (k -> v))
              case _				=> map
            }
        )
      } withBody {
        body.tail mkString "\n" // entfernt die erste Zeile und wandelt die Liste von Strings in einen String mit Zeilenumbrüchen um
      }
    }
    
    val mail: Email = """From: blinzler@googlemail.com
                         |Subject: Hallo
                         |
                         |Viel Spass mit Scala.
                         |
                         |Gruss
                         |Sebastian Kiaulens""".stripMargin
    println(mail)
  }
}

/**
 * Extraktor-Objekt
 */
object HeaderField {
  def unapply(s: String) = {
    val (key,value) = s.span(_!=':')
    if(key.isEmpty || value.isEmpty)
      None
    else
      Some((key,value.tail trim))
  }
}

object Email {
  def email(header: Map[String,String]) = new Email(header,"")
}

class Email(val header: Map[String,String], private[this] var b: String) {
  def body = b
  override def toString: String = header+"\n"+body
  def withBody(body: String) = {
    b = body
    this
  }
}