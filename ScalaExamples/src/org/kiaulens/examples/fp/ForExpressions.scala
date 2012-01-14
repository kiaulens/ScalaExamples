package org.kiaulens.examples.fp

/**
 * Ausdrücke mit dem Schlüsselwort for werden in Scala auch
 * als 'for-Comprehensions' bezeichnet.
 */
object ForExpressionsMain {
  def main(args: Array[String]) {
    // der Teilausdruck in Klammern wird als 'Generator' bezeichent und hat immer die Form:
    // <variablenname> <- <collection>
    // die lokal eingeführte Variable entspricht einem 'val'
    for (x <- 1 to 10) print(x+" ")
    println
    // mit 'Filter'
    for (x <- 1 to 10; if(x%2==1)) print(x+" ")
    println
    // oder Generator und Filter auf eigener Zeile
    for (
      x <- 1 to 10
      if(x%2==1)
    ) print(x+" ")
    println
    // Verwendung mehrerer Filter
    for (
      x <- 1 to 100
      if(x%2==1)
      if(x%5==0)
    ) print(x+" ")
    println
    // Verwendung mehrerer Generatoren (verhält sich wie zwei verschachtelte Schleifen)
    for {
      x <- 1 to 10
      t <- 2 to x-1
      if(x%t==0)
    } println(t+" teilt "+x)
    // Definition von Variablen (diese entspricht wieder einem val)
    for {
      x <- 1 to 10
      t <- 2 to x-1
      if(x%t==0)
      str = t+" teilt "+x
    } println(str)
    // zweiter Teil des 'for'-Ausdrucks als Block
    for {
      x <- 1 to 10
      t <- 2 to x-1
      if(x%t==0)
    } {
      print(t)
      print(" teilt ")
      println(x)
    }
    // das Schlüsselwort 'yield' sammelt die Ergebnisse jeden Schrittes in einer Collection
    // Erzeugen eines 'Vectors' von Paaren, bestehend aus dem jeweiligen Teiler 't'
    // und der Zahl 'x':
    val pairs = for {
      x <- 1 to 10
      t <- 2 to x-1
      if(x%t==0)
    } yield (t,x)
    println(pairs)
    // der Compiler wandelt das obige Beispiel etwa in Folgendes um:
    (1 to 10) flatMap (x => (2 to x-1) map (t => (t,x))) filter {case (t,x) => x%t==0}
    // 'for'-Comprehension für Map
    val holgersStudy = Study(
      Map(1 -> List(Lect("Prog 1",		true,	5),
                    Lect("OS 1",		false,	3),
                    Lect("Networks 1",	true,	3)),
          2 -> List(Lect("Prog 2",		true,	5),
                    Lect("OS 2",		false,	3),
                    Lect("Networks 2",	false,	3))))
    // Berechnung der Namen der von Holger erfolgreich absolvierten Vorlesungen
    val coursePassed = for {
      (_,courses) <- (holgersStudy semester)
      c <- courses
      if c.passed
    } yield c.name
    println(coursePassed)
    // Berechnung der Credit-Summe und Liste der bestandenen Vorlesungen
    val coursesPassedWithPoints = for {
      (_,courses) <- ((holgersStudy semester) toList)
      c <- courses
      if c.passed
      n = c.name
      p = c.creditPoints
    } yield (p,n)
    val creditPointsAndCoursesPassed =
      (coursesPassedWithPoints :\ (0,List[String]())) {
      case ((p,n),(sum,ns)) => (p+sum,n::ns)}
    println(coursesPassedWithPoints)
    println(creditPointsAndCoursesPassed)
  }
}

case class Lect(name: String, passed: Boolean, creditPoints: Int)
case class Study(semester: Map[Int,List[Lect]])
