package org.kiaulens.examples.fp

/**
 * Pattern-Matching mit Objekten selbst definierter Klassen
 */
object CaseClassMain {
  def main(args: Array[String]) {
    val printAllowedAtAgeOf: Movie => Unit = {
      case Movie(t,0) => println(t+" (keine Altersbeschraenkung)")
      case Movie(t,i) => println(t+" (frei ab "+i+")")
    }
    val movie = Movie("Am Limit",0)
    printAllowedAtAgeOf(movie)
    printAllowedAtAgeOf(new Movie("Matrix",16))
    println(movie.title)
    if(movie == Movie("Am Limit",0)) println("der selbe Film")
    println(movie)
    val movie2 = movie.copy(title = "Biene Maja")
    println(movie2)
  }
}

/**
 * Durch die Definition als Case-Klasse ist es möglich, auf ein Muster
 * der Form 'Movie(t,f)' mit einem 'String t' und einem 'Int f' zu matchen.
 * 
 * Desweiteren generiert der Compiler kanonische Implementierungen von 'toString',
 * 'equals', 'hashCode' und 'copy'. Die Klassenparameter werden automatisch zu 'val'-Feldern.
 * Bei Bedarf kann auch 'var' vorangestellt werden.
 * 
 * Der Compiler erzeugt außerdem ein Companion-Objekt mit einer 'apply'- und einer
 * 'unapply'_Methode. 'unapply' zerlegt das 'Movie'-Objekt in ein Tupel bestehend aus
 * den Klassenparametern (benötigt zum matching).
 */
case class Movie(title: String, fsk: Int)