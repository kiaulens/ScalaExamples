package org.kiaulens.examples.fp

/**
 * 'Currysierung' ist die Umwandlung einer Funktion mit mehreren Parametern in
 * eine Kette von Funktionen mit je einem Parameter (benannt nach "Haskell Brooks Curry").
 */
object CurryMain {
  def main(args: Array[String]) {
    // nicht currysierte Funktion add
    def add(x: Int, y: Int) = x + y
    
    // currysierte Variante der Funktion add
    def addc(x: Int) = (y: Int) => x + y
    val addc2 = addc(1)
    println(addc2(2))
    // bzw.
    def addc3(x: Int)(y: Int) = x + y
    println(addc3(1)(3))
    
    // currysierte Funktion kann aus nicht currysierter Funktion erzeugt werden
    def addCurried = (add _).curried
    println(addCurried(2)(3))
    
    // Verwendung currysierter Funktionen in Funktionen höherer Ordnung (keine Angabe von Platzhaltern notwendig)
    val add1 = addCurried(1)
    println((1 to 10).toList map add1)
    
    // dies funktioniert auch für die gesamte Parameterliste nicht currysierter Funktionen
    println(((1 to 10).toList :\ 0) (add))
    
    // Funktion mit zwei zweielementigen Parameterlisten
    def addTuple(a: Int,b: Int)(c: Int,d: Int) = (a + c,b + d)
    println(addTuple(1,2)(3,5)) // es handelt sich hier nicht um zwei Tupel, sonder zwei Parameterlisten
    def addTuple2(t1: (Int, Int))(t2: (Int, Int)) = (t1._1+t2._1,t1._2+t2._2)
    // oder: Zerlegung der Tupel durch pattern matching
    def addTuple3: ((Int,Int)) => ((Int,Int)) => ((Int,Int)) = {
      case (a,b) => {
        case (c,d) => (a+c,b+d)
      }
    }
    val t1 = (1,2)
    val t2 = (3,5)
    println(addTuple3(t1)(t2))
    println(addTuple3((1,2))((3,5)))
    println(addTuple3(1,2)(3,5))
  }
}