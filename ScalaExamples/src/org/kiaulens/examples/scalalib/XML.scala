package org.kiaulens.examples.scalalib

/**
 * Scala Support für XML.
 */
object XMLMain {
  def main(args: Array[String]) {
    // wesentliche Klassen des Packages 'scala.xml'
      // 'Node' - abstrakte Klasse als Basisklasse für alle XML-Knoten
      // 'NodeSeq' - repräsentiert eine Sequenz von Knoten
      // 'Text' - Knoten der nur Text enthält
    
    // XML-Elemente können direkt als Literale geschrieben werden
    <b>Hello World</b> // entspricht einem Objekt der Klasse 'scala.xml.Elem' mit dem Label 'b' und dem Nachfolger Hello World
    
    // 'brace escapes' - XML-Literal kann Scala-Ausdrücke in geschweiften Klammern enthalten
    <result>{5+7+9}</result>
    val list = (1 to 10).toList
    <result>
      Summe der Liste {list.toString} ist {list.sum}
    </result>
      
    // beliebige Verschachtelung von XML-Literalen und Brace Escapes
    def author(name: String) = 
      <author>
        { if(name == "Oliver") <me/>
        else <name>{name}</name> }
      </author>
    println(author("Oliver"))
    println(author("Jon Spencer"))
    
    // Zeichen in Strings wie <,>,& usw. werden automatisch in Escape-Sequenzen umgewandelt
    println(<a>{"</a>wieder raus & wieder rein<a>"}</a>)
    
    // gescheifte Klammern in XML ausgeben
    println(<i>def f() {{println("Hallo")}}</i>)
    
    // Extrahieren von Teilen mit einfachen Methoden in 'NodeSeq'
    val scalabook =
      <book lang="de">
        <title>Scala</title>
        <author>Oliver Braun</author>
      </book>
    val osgibook =
      <book lang="de">
        <title>OSGI für Praktiker</title>
        <authors>
          <author>Bernd Weber</author>
          <author>Patrick Baumgartner</author>
          <author>Oliver Braun</author>
        </authors>
      </book>
      
    // Extrahieren des Titels
    println(osgibook \ "title")
    
    // Extrahieren der Autoren
    println(osgibook \\ "author")
    
    // Zugriff auf die verschiedenen Autoren
    println((osgibook \\ "author")(2))
    for(author <- osgibook \\ "author")
      println("Autor: "+author.text)
      
    // Berechnung eines Attributs mit @
    println(osgibook \ "@lang")
    
    // Pattern Matching
    def countAuthors(xml: scala.xml.Node): Int = {
      xml match {
        case <book>{fields @ _*}</book> => {
          for(<authors>{authors @ _*}</authors> <- fields)
            return (authors \\ "author").length
          1
        }
      }
    }
    println(countAuthors(osgibook))
    println(countAuthors(scalabook))
    
    // Methoden des Objektes 'scala.xml.XML'
      // 'load' - liest XML direkt aus einer Datei, einem Input-Stream, einer URL oder anderen Quellen ein
      // 'loadFile' - wandelt eine XML-Datei in einen 'Node' um
      // 'save' - speichert einen XML-Knoten in eine Datei
      // 'loadString' - erzeugt aus einem String XML
  }
}