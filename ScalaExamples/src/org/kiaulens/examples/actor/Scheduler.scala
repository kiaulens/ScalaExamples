package org.kiaulens.examples.actor

/**
 * Standardmäßig wird der 'ForkJoinScheduler' verwendet, welcher einen Thread-Pool
 * verwaltet, in dessen Threads die Actors ablaufen.
 * 
 * Scheduler:
 * ForkJoinScheduler - Standard für Actors
 * SingleThreadedScheduler - Actors laufen in einem Thread
 * ResizableThreadPoolScheduler - Thread-Pool wird bei Bedarf vergrößert
 * DaemonScheduler - Standard für DaemonActors
 * 
 * Anstatt die scheduler-Methode jedesmal zu redefinieren, kann die JVM-Property
 * 'actors.enableForkJoin' auf false gesetzt werden. Damit wird der ResizableThreadPoolScheduler
 * zum Standardscheduler für Actors. Property 'actors.corePoolSize' zum Ändern der Größe
 * des genutzten Thread-Pools.
 */
object Counter6Main {
  
  import scala.actors._
  import scala.actors.Actor._
  
  case object Increment
  case object Stop
  
  class SharedCounter(i: Int) extends Actor {
    import scala.actors.scheduler.SingleThreadedScheduler
    // Redefinieren der scheduler-Methode, alle Actors laufen in einem Thread
    override def scheduler = new SingleThreadedScheduler
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