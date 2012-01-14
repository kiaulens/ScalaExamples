package org.kiaulens.examples.actor

/**
 * Eine Applikation wird, im Gegensatz zu Actor, auch dann beendet
 * wenn es noch laufende 'DaemonActor' hat. Das Interface ist gleich.
 */
object DaemonActorMain {
  import scala.actors._
  import scala.actors.Actor._
  
  case object Increment
  case object Value
  
  object SharedCounter extends DaemonActor {
    def act() = act(0)
    def act(count: Int) {
      react {
        case Increment	=> act(count+1)
        case Value		=> {
          sender ! count
          act(count)
        }
      }
    } 
  }
  
  def main(args: Array[String]) {
    SharedCounter.start()
    SharedCounter ! Increment
    SharedCounter ! Increment
    SharedCounter ! Value
    receive {
      case i => println("Value = "+i)
    }
  }
}