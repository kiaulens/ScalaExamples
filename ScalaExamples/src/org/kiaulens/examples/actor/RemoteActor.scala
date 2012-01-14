package org.kiaulens.examples.actor

case object Stop

/**
 * Stellt Methoden zur Kommunikation über das Netzwerk bereit.
 * Mit 'alive' wird ein Actor an einen Port gebunden. Mit 'register' wird der Name
 * registriert.
 */
object RemoteActor {
  import scala.actors.Actor.{actor, loopWhile, react, self}
  import scala.actors.remote.RemoteActor.{alive, register}
  
  def remoteActor(name: String, remoteName: Symbol) = actor {
    alive(9010)
    register(remoteName, self)
    println(name+": Waiting for messages ...")
    var continue = true
    loopWhile(continue) {
      react {
        case Stop => {
          println(name+": Exiting")
          continue = false
        }
        case i: Int	=> println(name+": "+i+": Int")
        case msg	=> println(name+": "+msg)
      }
    }
  }
  
  def main(args: Array[String]) {
    remoteActor("Echo1", 'echo1)
    remoteActor("Echo2", 'echo2)
  }
}