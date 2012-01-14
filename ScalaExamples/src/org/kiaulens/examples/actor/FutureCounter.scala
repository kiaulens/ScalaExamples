package org.kiaulens.examples.actor

/**
 * Möglichkeit die Antwort eines Threads direkt einem Bezeichner zuzuweisen, obwohl sie
 * noch nicht vorhanden ist. Senden einer Nachricht mit !!, gibt ein Future-Objekt zurück, das
 * den Wert kapselt, der in der Zukunft zurückkommen wird. Der Actor selbst wird nicht verändert.
 */
object FutureCounterMain {
  
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
          case Value		=> {
            println("SharedCounter is sleeping")
            Thread.sleep(1000)
            println("SharedCounter is awake")
            sender ! count
          }
          case Stop			=> continue = false
        }
      }
    }
  }
  
  def main(args: Array[String]) {
    SharedCounter.start()
    SharedCounter ! Increment
    SharedCounter ! Increment
    val value = SharedCounter !! Value
    println("Value-request sent")
    println("Value = "+value) // 0-stellige Funktion
    println("Value = "+value()) // value.apply() wird ausgewertet und blockiert solange bis der Wert verfügbar ist
    // mit 'value.isSet' kann überprüft werden, ob der Wert bereits verfügbar ist
    SharedCounter ! Stop
  }
}