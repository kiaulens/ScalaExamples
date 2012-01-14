package org.kiaulens.examples.actor

object CounterMain {
  
  import scala.actors._
  import scala.actors.Actor._
  
  case object Increment
  case object Value
  case object Stop
  
  object SharedCounter extends Actor {
    private[this] var count = 0
    def act() {
      var continue = true
      while(continue) {
        receive {
          case Increment	=> count += 1
          case Value		=> sender ! count
          case Stop			=> continue = false
        }
      }
    }
  }
  
  def printValueOfSharedCounter() = {
    SharedCounter ! Value
    receiveWithin(100) {
      case i: Int => println("value of shared counter: "+i)
      case TIMEOUT => println("shared counter not available")
    }
  }
  
  def main(args: Array[String]) {
    SharedCounter ! Increment
    SharedCounter ! Increment
    SharedCounter.start()
    printValueOfSharedCounter()
    SharedCounter ! Increment
    printValueOfSharedCounter()
    SharedCounter ! Increment
    SharedCounter ! Stop
    SharedCounter ! Increment
    printValueOfSharedCounter()
    SharedCounter.restart()
    printValueOfSharedCounter()
    SharedCounter ! Stop
  }
}