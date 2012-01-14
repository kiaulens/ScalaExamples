package org.kiaulens.examples.actor

/**
 * Actor, der auf zwei Messages reagiert.
 */
object TwoMessagesMain {
  import scala.actors._
  import scala.actors.Actor._
  
  class MyActor extends Actor {
    def act() {
      receive { // actor blockiert, bis eine String message kommt
        case msg: String => println("Actor: got String: "+msg)
      }
      receive {
        case i: Int => println("Actor: got Int: "+i)
      }
    }
  }
  
  def main(args: Array[String]) {
    val myActor = new MyActor
    myActor.start()
    myActor ! 1
    myActor ! "Hello"
  }
}