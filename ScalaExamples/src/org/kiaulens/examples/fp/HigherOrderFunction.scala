package org.kiaulens.examples.fp

/**
 * Funktionen höherer Ordung ('higher-order-functions')
 * 
 * sind Funktionen, die Funktionen als Parameter haben oder als Ergebnis zurückgeben.
 */
object HigherOrderFunctionMain {
  def main(args: Array[String]) {
    
    def funList(fun: Int => Int, list: List[Int]): List[Int] = {
      if (list.isEmpty) List()
      else fun(list.head) :: funList(fun, list.tail)
    }
    def inc(x: Int) = x + 1
    def double(x: Int) = x * 2
    def incList (list: List[Int]) = funList(inc, list)
    def doubleList(list: List[Int]) = funList(double,list)
    
    // oder kürzer (mit der bereits vordefinierten Higher-Order-Function 'map')
    def incList2 (list: List[Int]) = list map inc
    def doubleList2 (list: List[Int]) = list map double
    
    // oder noch kürzer (mit Funktionsliteralen)
    def incList3 (list: List[Int]) = list map (i => i + 1)
    def doubleList3 (list: List[Int]) = list map (i => i * 2)
    
    // oder noch viel kürzer (gebundene Variable i durch Platzhalter ersetzen ('partially applied function'))
    def incList4 (list: List[Int]) = list map (_ + 1)
    def doubleList4 (list: List[Int]) = list map (_ * 2)
    
    println(incList4(List(1,2,3)))
    println(doubleList4(List(1,2,3)))
    
    // 'partiell angewandte Funktionen'
    val add = (_: Int)+(_: Int)
    println(add(2,3)) // enspricht add.apply(2,3)
    
    // Funktionen für Listen
    // 'foreach'
    val list = List(1,2,3,4,5)
    list.foreach(e => println(e))
    // oder
    list.foreach(println(_))
    // oder
    list.foreach(println)
    // oder
    list foreach println
    
    // 'filter'
    println("list filter (_<3)")
    println(list filter (_<3))
    
    println("list filterNot (_<3)")
    println(list filterNot (_<3))
    
    // 'span', 'dropWhile' und 'takeWhile'
    // 'l span p' ist äqu. zu '(l takeWhile p, l dropWhile p)'
    println("list span (_%2==1)")
    println(list span (_%2==1))
    
    // 'partition'- teilt Liste in zwei Listen, wobei die Elemnte der Ersten das Prädikat erfüllen
    // und die der zweiten nicht
    println("list partition (_%2==1)")
    println(list partition (_%2==1))
    
    // 'forall' und 'exists' - ermitteln, ob ein Prädikat für alle bzw. für mindestens ein Element
    // erfüllt ist
    println("list forall (_<3)")
    println(list forall (_<3))
    println("list exists (_<3)")
    println(list exists (_<3))
    
    // 'Falten' einer Liste - Listenelemente mit einer Operation zusammenfassen
    // 'foldLeft' und 'foldRight'
    println("list.foldLeft(0) (_+_)")
    println(list.foldLeft(0) (_+_)) // berechnet die Summe aller Elemente der Liste 'list' mit Startwert 0
    // für 'foldLeft' und 'foldRight' werden die Operatoren /: und :\ verwendet
    println("(list :\\ 0) (_+_)")
    println((list :\ 0) (_+_))
    // oder
    println("(0 /: list) (_+_)")
    println((0 /: list) (_+_))
    println("(\"Anfang -> \" /: list) (_+_)")
    println(("Anfang -> " /: list) (_+_))
    println("(list :\\ \" <- Ende\") (_+_)")
    println((list :\ " <- Ende") (_+_))
    // für nicht-leere Listen gibt es noch 'reduceLeft' und 'reduceRight', welche keinen Startparameter benötigen
    
    // 'scanLeft' und 'scanRight' - berechnen Liste aller Zwischenergebnisse
    println("(list scanLeft \"Anfang -> \") (_+_)")
    println((list scanLeft "Anfang -> ") (_+_))
    println("(list scanRight \" <- Ende\") (_+_)")
    println((list scanRight " <- Ende") (_+_))
    
    // Funktionen mit Funktion als Ergebnis
    // Beispiel: 'mkAdder' liefert eine Funktion, welche x zum jeweiligen Parameter addiert
    def mkAdder(x: Int) = (y: Int) => x + y
    val plus2 = mkAdder(2)
    println(plus2(5))
    
    // Beispiel zur Mächtigkeit von 'high order functions'
    println((1 to 10).toList map (x => x+(_: Int)) map (_(3)))
    // der erste 'map'-Aufruf erzeugt aus der Liste der Zahlen 1 bis 10
    // adder-Funktionen (s.o. 'mkAdder'). Jede dieser Funktionen aus der Liste
    // wird dann auf die Zahl 3 angewandt mit 'map (_(3))'
  }
}
