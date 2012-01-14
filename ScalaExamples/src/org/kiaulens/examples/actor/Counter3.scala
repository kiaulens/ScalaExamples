package org.kiaulens.examples.actor

/**
 * Eine Applikation mit vielen 'SharedCounter'-Actor und Ausgaben, welcher Thread
 * gerade läuft. Statt 'receive' wird 'react' verwendet und statt 'while' 'loopWhile'.
 * Actors benötigen mit dieser Methode keinen eigenen Thread (Vermeidung von Overhead).
 */
object Counter3Main {
  
  import scala.actors._
  import scala.actors.Actor._
  
  case object Increment
  case object Stop
  
  class SharedCounter(i: Int) extends Actor {
    private[this] var count = 0
    def act() {
      var continue = true
      loopWhile(continue) {
        react {
          case Increment	=> {
            println("Actor "+i+": "+Thread.currentThread)
            count += 1
          }
          case Stop			=> continue = false
        }
      }
      // this will never be executed
      println("Actor "+i+" stopped")
    }
  }
  
  def main(args: Array[String]) {
    val counters = for (i <- 0 to 9) yield new SharedCounter(i)
    for (i <- 0 to 9) counters(i).start()
    for (i <- 0 to 9) counters(i) ! Increment
    for (i <- 0 to 9) counters(i) ! Increment
    Thread.sleep(1000)
    for (i <- 0 to 9) counters(i) ! Stop
  }
}