package org.kiaulens.examples.fp

/**
 * 'Pattern Matching', der vergleich mit einem Muster, ist eine Art
 * verallgemeinerte 'switch-case'-Anweisung. Eine Variable kann auf
 * verscheidene Werte überprüft werden.
 */
object PatternMatchingMain {
  def main(args: Array[String]) {
    
    val number = 3
    number match {
      case 1 => println("eine Eins")
      case 2 => println("eine Zwei")
      case _ => println("etwas anderes") // default, 'Wildcard-Pattern' passt auf alles
    }
    
    // Funktion, welche mittels 'patter matching' ihr Argumente unterschiedet
    def checkNumber(number: Int) = number match {
      case 1 => println("eine Eins")
      case 2 => println("eine Zwei")
      case _ => println("etwas anderes")
    }
    checkNumber(2)
    
    // 'checkNumber' als Funktionsliteral
    val checkNumberL = (number: Int) => number match {
      case 1 => println("eine Eins")
      case 2 => println("eine Zwei")
      case _ => println("etwas anderes")
    }
    checkNumberL(1)
    
    // 'checkNumber' als Funktionsliteral ohne expliziete Parameterangabe
    val checkNumberL2: Int => Unit = {
      case 1 => println("eine Eins")
      case 2 => println("eine Zwei")
      case number => println("etwas anderes: "+number) // auf eine Variable kann alles gematcht werden
    }
    checkNumberL2(5)
    
    // Typ eines Wertes ermitteln
    val checkValue: Any => Unit = {
      case i: Int		=> println("ein Int: "+i)
      case d: Double	=> println("ein Double: "+d)
      case _			=> println("etwas anderes")
    }
    checkValue(5)
    checkValue(5.)
    checkValue("5")
    
    // verschiedene Muster gemischt in einer Funktion
    val checkValue2: Any => Unit = {
      case 1			=> println("eine Eins")
      case _: Int		=> println("ein Int")
      case d: Double	=> println("ein Double: "+d)
      case any			=> println("etwas anderes: "+any)
    }
    checkValue2(1)
    checkValue2(2)
    checkValue2(2.)
    checkValue2("5")
    
    // 'pattern matching' auf Wert und Typ (List)
    val checkLists: List[Any] => Unit = {
      case Nil				=> println("eine leere Liste")
      case List(_,2,_)		=> println("eine 3-elementige Liste mit einer 2 an Position 2")
      case List(_,_,_)		=> println("eine 3-elementige Liste")
      case _ :: _ :: Nil	=> println("eine 2-elementige Liste")
      case _ :: _			=> println("eine nichtleere Liste")
    }
    checkLists(List())
    checkLists(List(1,2,3))
    checkLists(List("a","b","c"))
    checkLists(List(1,2))
    checkLists(List(1))
    
    // Achtung: Type Erasure
    val doesNotWorkAsExpected: List[Any] => Unit = {
      case _: List[String]	=> println("eine Liste mit Strings")
      case _:List[Int]		=> println("eine Liste mit Ints")
    }
    doesNotWorkAsExpected(List(1,2,3)) // der zweite Fall wird nie erreicht
    
    val worksAsExpected: List[Any] => Unit = {
      case (x:String)::_	=> println("eine Liste mit einem String als erstes Element")
      case _				=> println("eine andere Liste")
    }
    worksAsExpected(List("Hallo",1,true,3.4))
    
    // matching auf Tupel
    val checkValue3: Any => Unit = {
      case (1,"Hallo")	=> println("ein Tupel mit einer 1 und \"Hallo\"")
      case (_,1.2)		=> println("ein Tupel mit dem Wer 1.2 an zweiter Stelle")
      case (_,_,_)		=> println("ein Tripel")
      case i: Int		=> println("ein Int: "+i)
      case _			=> println("etwas anderes")
    }
    checkValue3((1,"Hallo"))
    checkValue3(("Hallo",1.2))
    checkValue3(("Hallo",1.2,true))
    checkValue3((1))
    checkValue3(List("Hallo",1.2))
    
    // 'Guards' - werden zusätzlich zum pattern ausgewertet
    val checkValue4: Any => Unit = {
      case (x:Int,y:Int) if x*y > 0	=> println("gleiche Vorzeichen");
      case (_:Int,_:Int)			=> println("verschiedene Vorzeichen")
      case _						=> println("kein Int-Tupel")
    }
    checkValue4((-1,4))
    checkValue4((1,4))
    checkValue4((-1.1,4))
    
    // der 'Option'-Typ wird verwendet, wenn das Ergebnis ein optionaler Wert ist
    // Beispiel: Verwendung des Optionen-Typs in der Scala Collection-Lib.
    val cities = Map("A" -> "Augsburg", "B" -> "Berlin")
    val isCity: Option[Any] => Boolean = {
      case Some(_)	=> true
      case None		=> false
    }
    println(isCity(cities get "A"))
    println(isCity(cities get "C"))
    println(cities get "A")
    println(cities get "C")
  }
}