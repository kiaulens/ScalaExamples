package org.kiaulens.examples.actor
import scala.actors._
import scala.actors.Actor._

/**
 * Actor-Kommunikation funktioniert über Messages. Diese kann ein beliebiges Objekt sein.
 * Üblicherweise werden Case-Klassen und -Objekte bzw. Strings, Tupel, etc. genutzt, um
 * mit Pattern Matching Reagieren zu können.
 * 
 * Mit 'actor ! message' wird dem Actor 'actor' die Message 'message' geschickt.
 */
object ReceiveReactMain {
  def main(args: Array[String]) {
    self ! "Hello" // 'self' entspricht einer Methode aus dem Actor-Companion-Objekt, die den Actor der gerade ausgeführt wird zurückgibt
    receive {
      case msg => println("Received message: "+msg)
    }
    // passt eine Message nicht auf die in 'receive' definierten Fälle, wird sie ignoriert
    /* self ! 1
    receive { // receive blockiert solange, bis eine passende Message aus der Mailbox entnommen werden kann
      case msg:String => println("Received message: "+msg)
    } */
    
    // timeout für das Empfangen einer Message:
    var myActor = new MyActor(100)
    myActor.start()
    myActor ! "Hello"
    
    myActor = new MyActor(0)
    myActor ! "Hello Again"
    myActor.start()
    
    myActor = new MyActor(100)
    myActor.start()
  }
}

class MyActor(timeout: Long) extends Actor {
  def act() {
    println("Actor started, waiting "+timeout+"ms for strings")
    receiveWithin(timeout) {
      case msg: String	=> println("Actor: received "+msg)
      case TIMEOUT		=> println("Actor: TIMEOUT") // 'TIMEOUT' ist ein normales Objekt
    }
  }
}