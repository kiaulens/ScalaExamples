package org.kiaulens.examples.actor

/**
 * Eine Applikation mit vielen 'SharedCounter'-Actor und Ausgaben, welcher Thread
 * gerade läuft. Jeder Actor hat einen eigenen Thread.
 */
object Counter2Main {
  
  import scala.actors._
  import scala.actors.Actor._
  
  case object Increment
  case object Stop
  
  class SharedCounter(i: Int) extends Actor {
    private[this] var count = 0
    def act() {
      var continue = true
      while(continue) {
        receive {
          case Increment	=> {
            println("Actor "+i+": "+Thread.currentThread)
            count += 1
          }
          case Stop			=> continue = false
        }
      }
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