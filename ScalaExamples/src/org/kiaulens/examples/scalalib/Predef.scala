package org.kiaulens.examples.scalalib

/**
 * Ein Überblick über das Predef-Objekt
 */
object PredefMain {
  def main(args: Array[String]) {
    println("Hello".isInstanceOf[AnyRef])
    println("Hello".isInstanceOf[ScalaObject]) // bestimmt, ob es sich um eine Scala-Klasse oder nicht handelt
    println("Hello".asInstanceOf[AnyRef])
    
    1.7.asInstanceOf[Int]
    // entspricht
    1.7.toInt
    
    println(List(1,2,3).isInstanceOf[ScalaObject])
    println("Hello World".isInstanceOf[ScalaObject])
    
    // Das Predef-Objekt
    // wird automatisch mit importiert
    // Inhalt:
      // Typsynonyme
        // type String		= java.lang.String
        // type Class[T]	= java.lang.Class[T]
        // type Map[A, +B]	= collection.immutable.Map[A, B]
        // type Set[A]		= collection.immutable.Set[A]
      // alles was im Package-Object 'scala' definiert wurde, ist auch in Predef sichtbar
      // Methoden für Fehler oder Zusicherungen (assertions)
        // 'error', 'exit', 'assert', 'assume' und 'require'
        def assert(assertion: Boolean) {
          if (!assertion)
            throw new java.lang.AssertionError("assertion failed")
        }
      // weitere Typsynonyme
        // 'Pair' für 'Tuple2' und 'Triple' für 'Tuple3'
      // 'ArrowAssoc' und Map
      Map(Pair(1,"Eins"), Pair(2,"Zwei"))
        // mit spezieller Tupel Syntax
      Map((1,"Eins"), (2,"Zwei"))
        // und normalerweise
      Map(1 -> "Eins", 2 -> "Zwei")
      // Die Klasse 'ArrowAssoc' definiert eine Methode mit dem Namen ->, welche aus dem
      // Objekt, auf das die Methode angewendet wird, und dem Parameter ein Tupel erzeugt.
      // Die '1' wird Instanz der Klasse 'ArrowAssoc', durch implizite Konversion:
      implicit def any2ArrowAssoc[A](x: A): ArrowAssoc[A] = new ArrowAssoc(x)
      // Funktionen zum Einlesen und Ausgeben:
      // 'println', 'print', 'readLine', 'readInt' und viele mehr
      // sind in Objekt 'Console' definiert
  }
}

/**
 * Methoden der Wurzelklasse 'Any'
 */
abstract class Any {
  
  // kann nach Bedarf redefiniert werden
  def equals(that: Any): Boolean
  
  // Objecte werden mit == und != verglichen
  // durch das Schlüsselwort 'final' können diese Methoden nicht redefiniert werden
  final def == (that: Any): Boolean =
    if (null eq this) null eq that else this equals that
  final def != (that: Any): Boolean = !(this == that)
  
  // sollte mit equals einhergehen (gleiche Objekte sollten den gleichen hashCode besitzen)
  def hashCode: Int
  def toString: String
  
  // Überprüft ob ein Objekt Instanz einer bestimmten Klasse ist
  def isInstanceOf[A]: Boolean
  
  // wird zum casten eines Objektes auf eine andere Klasse verwendet
  def asInstanceOf[A]: A
}

/**
 * Die Klasse 'AnyRef' definiert alle abstrakten Methoden aus 'Any', sowie 'eg' und 'ne'
 */
class AnyRef {
  def equals(that: Any): Boolean = this eq that // gibt true zurück, wenn this und that dasselbe Objekt referenzieren
  // final def eq(that: AnyRef): Boolean = ...
  final def ne(that: AnyRef): Boolean = !(this eq that)
  // def hashCode: Int = ...
  // def toString: String = ...
}