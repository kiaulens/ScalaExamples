package org.kiaulens.examples.actor

/**
 * Ein Reactor ist ein leichtgewichtiger Actor.
 * Der Sender wird nicht impliziet mit der Message übertragen.
 * Es gibt nur 'react', kein 'receive', d.h. kein exklusiven Thread.
 * Der Reactor verwaltet wenider Zustand als der Actor.
 * Soll ein Sender übertragen werden, kann der Subtrait 'ReplyReactor' genutzt werden.
 */
object StringBuildReactor {
  import scala.actors.Reactor
  import scala.actors.Actor._
  
  object MyStringBuildReactor extends Reactor[String] {
    def act() = act(new StringBuilder)
    def act(str: StringBuilder) {
      react {
        case ""	=> println(str)
        case s	=> act(str ++= s)
      }
    }
  }
  
  def main(args: Array[String]) {
    MyStringBuildReactor.start()
    "Hello world and hello reader!" split " " map {str => str.head.toUpper + str.tail} foreach
    {MyStringBuildReactor ! _}
    MyStringBuildReactor ! ""
  }
}