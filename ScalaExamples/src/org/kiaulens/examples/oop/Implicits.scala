package org.kiaulens.examples.oop

/**
 * "implizite Umwandlung" - Ein Objekt kann bei Bedarf automatisch
 * in ein anderes Objekt einer anderen Klasse umgewandelt werden.
 * Daher ist es möglich bestehende Klassen durch zusätzliche Methoden anzureichern.
 * Die anreichernden Klassen werden als "Rich-Wrapper" bezeichnet.
 */
object ImplicitsMain {
  def main(args: Array[String]) {
    val str = "Hello World" // ist vom Typ 'java.lang.String'
    val dstr = str drop 6 // die Methode 'drop' ist nicht in 'java.lang.String' enthalten
    // println(dstr)
    // das 'String'-Objekt 'str' wird automatisch in ein 'StringOps'-Objekt umgewandelt
    // drop liefert als Ergebnis wieder einen String
    
    // Beispiel: Bands und Platten
    val jl = new Band("The Jesus Lizard")
    jl.addRecord(new Record("Goat")) // wäre angenehmer 'jl.addRecord("Goat")' zu schreiben
    implicit def stringToRecord(title: String) = new Record(title)
    jl.addRecord("Pure")
    println(jl)
    
    // weitere Anwendung von Implicits
    Map("A" -> "Augsburg", "B" -> "Berlin") // die Zeichenkette vor dem Pfeil wird in ein 'ArrowAssoc' umgewandelt, welche die Methode '->' hat
    
    // implizite Parameter - Parameter einer Funktion, die beim Aufruf nicht angegeben werden müssen,
    // sondern durch den Compiler ergänzt werden (der Compiler wählt die impliziten Parameter nur durch den Typ!)
    val myRecords = new RecordLibrary
    // myRecords.addInteractive(new ReadLineInputPrompt("\n>")) // Nachteil: Input-Objekt muss immer wieder angegeben werden beim Aufruf
    implicit val bandInput = new BandnameInput(new ReadLineInput)
    implicit val recordtitleInput = new RecordtitleInput(new ReadLineInputPrompt("\n>"))
    myRecords.addInteractive()
    println(myRecords)
  }
}

class Record(title:String) {
  override def toString = title
}

class Band(name:String) {
  private var records: List[Record] = List()
  def addRecord(record: Record) {
    records ::= record
  }
  override def toString = name+": "+records
}

/**
 * Da der Compiler implizite Parameter nur nach dem Typ auswählt,
 * wurden zwei Wrapperklassen mit selten benutzen Namen erstellt.
 */
class BandnameInput(val input: Input)
class RecordtitleInput(val input: Input)

class RecordLibrary {
  private var records = Map[String,List[String]]()
  def addInteractive (overrideEntry: Boolean = false) (implicit bandIn: BandnameInput, recordIn: RecordtitleInput) = {
    print("Bandname: ")
    val bandname = bandIn.input.get()
    print("Recordtitle: ")
    val recordtitle = recordIn.input.get()
    records += bandname -> (records get bandname match {
      case None		=> List(recordtitle)
      case Some(rs)	=> if (overrideEntry) List(recordtitle) else (recordtitle :: rs)
    })
  }
  override def toString = records.toString
}