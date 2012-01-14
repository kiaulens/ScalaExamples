package org.kiaulens.examples.actor

/**
 * Client Seite des RemoteActors [[org.kiaulens.examples.actor.RemoteActor]].
 */
object RemoteActorClient extends Application {
  import scala.actors.Actor.actor
  import scala.actors.remote.Node
  import scala.actors.remote.RemoteActor.select
  
  val node = Node("127.0.0.1", 9010)
  
  actor {
    val c = select(node, 'echo1)
    val d = select(node, 'echo2)
    
    c ! "Hello RemoteActor"
    d ! "Hello RemoteActor"
    for (i <- 1 to 10) {
      c ! i
      d ! i
    }
    c ! Stop
    d ! Stop
  }
}