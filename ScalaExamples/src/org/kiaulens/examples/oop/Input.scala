package org.kiaulens.examples.oop

/**
 * Beispiel: verschachtelte Modifikationen (mit Traits)
 */
object InputMain {
  def main(args: Array[String]) {
    
    /*println("ReadLineInput")
    (new ReadLineInput).get()
    
    println("ReadLineInput mit Echo")
    (new ReadLineInput with Echo).get()
    
    println("ReadLineInputPrompt")
    (new ReadLineInputPrompt(">")).get()
    
    println("ReadLineInputPrompt mit Echo")
    (new ReadLineInputPrompt(">") with Echo).get()*/
    
    /*val in = new MyInput
    in.get()
    in.get()
    println(in.inputList)*/
    
    val inBEC = new ReadLineInputPrompt("BEC>") with Blacklist with Echo with Collect {
      val blacklist = List("Hello")
    }
    inBEC.get()
    println(inBEC.inputList)
    
    val inCBE = new ReadLineInputPrompt("CBE>") with Collect with Blacklist with Echo {
      val blacklist = List("Hello")
    }
    inCBE.get()
    println(inCBE.inputList)
    // welche 'get'-Methode aufgerufen wird, entscheidet Scala mit der "Linearisierung"
    // beim Objekt 'inBCE' ist das Resultat der Linearisierung (traits werden von links nach rechts linearisiert):
    // Echo -> Blacklist -> Collect -> ReadLineInputPrompt -> ReadLineInput -> Input -> AnyRef -> Any
    // d.h. bei 'inCBE.get()' wird zuerst 'get' aus 'Echo' aufgerufen, dann mit 'super.get()'
    // die Methode aus 'Blacklist' und diese wiederrum 'get' aus 'Collect'
  }
}

abstract class Input {
  def get(): String
}

class ReadLineInput extends Input {
  def get() = readLine()
}

class FileInput(filename: String) extends Input {
  import scala.io.Source
  import java.io.File
  private[this] val contents = Source fromFile (new File(filename)) getLines
  def get() = {
    if (contents.hasNext) contents.next() else ""
  }
}

class ReadLineInputPrompt(prompt: String) extends ReadLineInput {
  override def get() = {
    print(prompt+" ")
    super.get()
  }
}

// stackable Modifications - Traits können Methoden von Klassen Modifizieren, welche hintereinander geschachtelt werden können
/**
 * Gibt Zeichenkette beim Einlesen auf der Konsole aus.
 * 
 * Der trait 'Echo' kann nur von einer Klasse implementiert werden,
 * wenn diese die get-Methode implementiert hat.
 */
trait Echo extends Input {
  abstract override def get() = {
    val input = super.get()
    println(input)
    input
  }
}

/**
 * Speichern und Möglichkeit des späteren Abrufs der Eingaben.
 */
trait Collect extends Input {
  import scala.collection.mutable.ListBuffer
  private[this] var inputs = new ListBuffer[String]
  def inputList = inputs.toList
  abstract override def get() = {
    val input = super.get()
    inputs += input
    input
  }
}

/**
 * Hintereinanderschaltung der Traits 'Echo' und 'Collect'
 */
class MyInput extends ReadLineInputPrompt(">") with Collect with Echo

/**
 * Ist die eingegebene Zeichenkette in der Blacklist vorhanden, soll sie durch
 * 7 '*' ersetzt werden.
 */
trait Blacklist extends Input {
  val blacklist: List[String]
  abstract override def get() = {
    val input = super.get()
    if (blacklist contains (input trim))
      "*******"
    else
      input
  }
}