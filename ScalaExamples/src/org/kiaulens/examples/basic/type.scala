package org.kiaulens.examples.basic;
abstract class `type` {
  
  // Scala ist statisch typisiert, kann aber mit einem Typinferenzmechanismus oft den Typ selbst ermitteln
  // Variablen-Definitionen müssen immer mit 'var' oder 'val' beginnen ('var' ist veränderbar, 'val' entspricht 'final')
  
  val age: Int // diese Variable muss im Kontext einer Klasse oder eines Traits deklariert werden
  // kann der Typ inferiert werden, muss der Doppelpunkt weggelassen werden, z.B.:
  var sum = 0
  // oder mehrfachzuweisung
  val i, j = 12
  
  // bei parameterisierten Typen steht der Typparameter in eckigen Klammern, z.B.:
  var list: List[Int] = List() // veränderbare Variable 'list' vom Typ "Liste von Integer"
  var listt: List[Int] = List[Int]() // Typ kann auch angegeben werden, ist aber auf Grund der Typinferenz nicht nötig
  // oder Liste mit Integer-Werten initialieren (Typ muss nicht engegeben werden):
  var listinit = List(1,2,3)
  
  // in Scala werden Objekte mit 'new' erzeugt. Die kürzere Schreibweise im obigen Listen-Beispiel kann überall angewandt werden,
  // wenn die Methode 'apply' definiert wurde. 'List()' expandiert automatisch zu 'List.apply()'. 'apply' erzeugt automatisch
  // ein neues leeres Object und gibt dieses zurück. Abstrakte Klassen können nicht mit 'new' erzeugt werden (z.B. 'List').
  // Eine spezielle Implementierung (z.B. 'Java-ArrayList') kann so genutz werden:
  val listimpl = new java.util.ArrayList[Int]()
  // oder "val listimp = new ArrayList[Int]()" mit "import java.util.ArrayList"
  val array = Array(1,2,3) // ein Array der Länge 3 mittels 'Array.apply' erzeugt
  val arrayy = new Array[Int](5) // bei einem mit 'new' erzeugten Array entspricht der Parameter der Länge des Arrays
  // bei der Deklaration mit 'val' ist nur die Referenz auf das Array unveränderlich, Werte können mit:
  arrayy(0) = 6
  // in das Array geschrieben werden. Dieser Aufruf wird zu "array.update(0,6)" expandiert. Dies funktioniert für alle
  // Klassen die die Methode update implementieren.
  
  // ListBuffer wird genutzt im Elemente nach und nach zu einer Liste zusammen zu fügen:
  val listbuffer = scala.collection.mutable.ListBuffer[Int]() // leerer ListBuffer wird über 'apply'-Methode erzeugt
  listbuffer += 1 // die Zahl 1 wird angehängt
  listbuffer ++= List(2,3,4) // die Zahlen 2,3,4 werden angehängt
  val listtt = listbuffer.toList // aus dem ListBuffer wird List(1,2,3,4)
  // i++ in Scala nicht erlaubt!!!
  // "listbuffer += 1" steht für  "listbuffer.+=(1)", d.h. eine Methode mit namen += und dem Argument 1
  // Methoden können fast beliebige Namen erhalten und Methoden mit nur einem Argument können in Infix-Operator-Schreibweise erfolgen
  // Operatoren sind eigentlich gewöhnliche Methoden
  
  // der Cons-Operator
  1 :: List(2,3,4) // erzeugt die Liste List(1,2,3,4)
  // der obige Oerator ist in der Klasse List implementiert: "List(2,3,4).::(1)"
  // jeder Operator der auf einen Doppelpunkt endet wird als Methode des rechten Operanden interpretiert
  
  // Regeln für Bezeichner:
  // 1. das erste Zeichen ist ein Buchstabe: darauf können beliebige Zahlen und Buchstaben folgen.
  // Auf einen Unterstrich _ können entweder wieder Buchstaben und Zahlen oder Operatorzeichen folgen:
  // sum, unary_-
  // 2. das erste Zeichen ist ein Operatorzeichen: beliebige Folge von Operatorzeichen kann folgen: ::, ++=
  // 3. eine beliebige Zeichenkette in Backquotes: `def`, `class`
  
  // zulässige Operatorzeichen: ! # % & * + - / : < = > ? @ \ ^ | ~
  
  // mehrzeilige Zeichenketten
  """Dies ist
  |eine mehrzeilige
  |Zeichenkette""".stripMargin
  
  // Tupel
  val myTuple = ("Hello", 1, true, 5.7) // dieses Tupel hat den Typ "(String, Int, Boolean, Double)"
  // Zugriff beginnt bei 1 mit _
  val first = myTuple._1 // usw.
  // oder
  val (firstt, second, third, fourth) = myTuple // Zuweisung aller Werte auf einmal
  
  // Mengen
  val m, n = scala.collection.mutable.Set[Int]() // m und n wird eine veränderliche Leere Menge zugewiesen
  m += 12 // das von m referenzierte Set wird verändert mit "m.+=(12)"
  println("m = " + m+"\nn = "+n) // Ausgabe:
  // m = Set(12)
  // n = Set()
  
  // Funktionsdefninition:
  def hello(name: String) {
    println("Hello "+name)
  }
  
  // Funktionsaufruf:
  hello("Scala-Neuling")
  
  // Funktionsdefinition mit explizietem Rückgabetyp 'Unit' (entpricht 'void' in Java). wird in Scala "Prozedur" genannt
  def helloret(name: String): Unit = {
    println("Hello "+name)
  }
  
  // eine Funktion:
  def sum(x: Int, y:Int): Int = {
    return x+y
  }
  // soll der zuletzt berechnete Ausdruck zurückgegeben werden kann 'return' auch weggelasen werden
  def summ(x: Int, y:Int): Int = {
    x+y
  }
  // bei nur einem Ausdruck können auch Klammern weggelassen werden. in vielen Fällen kann auch der Typ weggel. werden
  def summm(x: Int, y:Int) = x+y
  
  // named arguments und default arguments
  sum(y=2,x=1) // reihenfolge der argumente kann vertauscht werden
  def sumdefault(x: Int = 1, y:Int = 2) = x+y
  sumdefault(x=1)
  
  // variable Anzahl von Parametern
  def printInts(ints: Int*) { // ein Array kann der Funktion aber nicht übergeben werden, da sie die Argumente einzeln erwartet
    for (int <- ints) println(int)
  }
  val numbers = Array(1,2,3,4)
  printInts(numbers: _*) // _* wandelt das Array in einzelne Werte um
  printInts(List(100,200,300): _*) // funktioniert auch bei anderen Collections
  printInts() // kann auch ohne Argumente aufgerufen werden
  
  def printStringAndInts(string: String, ints: Int*) { // Beispiel einer funktion mit fest vorgegebenem und variablem Parameter
    // ...
  }
  
  // Beispiel einer Funktion die eine Funktion enthält
  def haveSameElements(xs:List[Any],ys:List[Any]) = { // der Typ 'Any' ist Supertyp aller Scala-Typen
    def isSubsetOf(xs:List[Any],ys:List[Any]): Boolean = {
      for (x <- xs)
        if (!(ys contains x)) return false
      true
    }
    isSubsetOf(xs,ys) && isSubsetOf(ys,xs)
  }
  
  // Typsynonyme mit dem Schlüsselwort 'type'
  type IntList = List[Int]
  
  type ProductName = String
  type Cent = Int
  type PriceList = Map[ProductName, Cent] // Beispiel einer Produkt-Preisliste
}