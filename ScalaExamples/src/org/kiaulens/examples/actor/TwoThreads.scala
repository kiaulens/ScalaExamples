package org.kiaulens.examples.actor

/**
 * Ein Actor kommuniziert mit anderen Actors nur über Messages (asynchron).
 * Es werden nicht wie in Java gemeinsame Daten genutzt.
 * Ein Thread ist ein Actor (nicht umgekehrt).
 * Der mit 'actor' erzeugte Actor wird sofort gestartet.
 */
object TwoThreadsMain {
  def main(args: Array[String]) {
    
    import scala.actors.Actor.actor
    println("Main: gestartet")
    
    // der Parameter der Funktion 'actor' hat den Typ '=> Unit'
    // es ist ein By-Name-Parameter, der erst in der 'actor'-Funktion ausgeführt wird
    actor {
      println("Actor: gestartet")
      for (i <- 1 to 4) {
        Thread.sleep(1000)
        println("Actor: "+i)
      }
      println("Actor: fertig")
    }
    
    for (i <- 1 to 4) {
      Thread.sleep(1000)
      println("Main: "+i)
    }
    println("Main: fertig")
  }
}