package org.kiaulens.examples.actor

/**
 * Ein Actor, der expliziet gestartet werden muss.
 */
object ThreeThreadsMain {
  import scala.actors.Actor
  
  class MyActor(number: Int) extends Actor {
    def act() {
      println("Actor"+number+": gestartet")
      for (i <- 1 to 2) {
        Thread.sleep(1000)
        println("Actor"+number+": "+i)
      }
      println("Actor"+number+": fertig")
    }
  }
  
  def main(args: Array[String]) {
    println("Main: gestartet")
    for (i <- 1 to 3)
      new MyActor(i).start()
      
    for (i <- 1 to 2) {
      Thread.sleep(1000)
      println("Main:   "+i)
    }
    
    println("Main: fertig")
  }
}