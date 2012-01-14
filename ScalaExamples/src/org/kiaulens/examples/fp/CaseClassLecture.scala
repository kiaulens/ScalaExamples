package org.kiaulens.examples.fp

/**
 * Versiegelte Klassen
 * 
 * in diesem Beispiel führt die mit dem Schlüsselwort 'sealed' gekennzeichnete
 * Klasse 'Lecture' dazu, dass die Ableitung von Subklassen unterbunden wird (abgeleitete
 * Klassen müssen in der selben Sourcecode-Datei stehen). Dies führt wiederrum dazu, dass
 * in der Pattern-Matching Funktion 'courseTitle' eine Warnung vom Compiler erzeugt wird,
 * wenn nicht alle Muster (Subklassen von Lecture) berücksichtigt werden. Dies ist allerdings
 * nur auf direkt abgeleitete Case-Klassen beschränkt.
 * 
 * 
 * 
 */
object CaseClassLectureMain {
  def main(args: Array[String]) {
    
    def courseTitle(lecture: Lecture) = lecture match {
        case Course(title) => title
        case Exercise(Course(title)) => title
        //case Tutorial(Course(title)) => title
    }
    val course = Course("funktionale Programmierung")
    println(courseTitle(course))
    println(courseTitle(Exercise(course)))
    //println(courseTitle(Tutorial(course)))
      
    // sollen bewusst einzelne Fälle ausgelassen werden, weil sie nicht vorkommen können
    // kann dies dem Compiler mit der '@unchecked'-Annotation mittgeteilt werden
    def nonEmptyListMatch(l: List[Any]) = (l: @unchecked) match {
      case 1::_ => println("Liste beginnt mit 1")
      case _::_ => println("Liste beginnt nicht mit 1")
    }
    
    // partielle Funktion
    // alle Case-Klassen werden abgedeckt, aber innerhalb der Klassen nicht alle Fälle
    // die partielle Funtkion hat zwei Typ-Parameter: den Argumenttyp und den Ergebnistyp
    // in dem unteren Beispiel wird eine partielle Funktion von 'Lecture' nach 'String' definiert
    def courseTitle2: PartialFunction[Lecture,String] = {
      case Course(title) => title
      case Exercise(Course(title)) => title
      case Tutorial(Course("Prog 2")) => "Prog 2"
    }
    // matchError wird immernoch erzeugt mit:
    // courseTitle2(Tutorial(Course("Prog 1")))
    
    // dynamisch festellen ob die partielle Funktion definiert ist
    println(courseTitle2.isDefinedAt(Tutorial(Course("Prog 1"))))
    
    // andere Methoden des Traits PartialFunction
    // 'andThen' - komponiert die partielle Funktion mit einer Funktion, die das Ergebnis transformiert
    println((courseTitle2 andThen (_.map(_.toUpper)))(Course("Programmierung 2")))
    // 'orElse' - komponiert die partielle Funktion mit einer Funktion, die berechnet wird,
    // wenn die partielle Funktion für das Argument nicht definiert ist
    val unknown: PartialFunction[Lecture, String] = {case _ => "unkown title"}
    println((courseTitle2 orElse unknown)(Tutorial(Course("Programmierung 2"))))
    // 'lift' - macht aus der partiellen Funktion eine totale Funktion, die für definierte
    // Werte 'x' das Ergebnis 'Some(x)' und für nicht definierte 'None' zurückliefert
    println((courseTitle2 lift)(Course("Prog 1")))
    println((courseTitle2 lift)(Tutorial(Course("Prog 1"))))
    
    // Variablenname für (Teil)-Pattern
    def courseTitle3: PartialFunction[Lecture,String] = {
      case Tutorial(course @ Course("Prog 2")) => "In diesem Semester: Tutorial zur Vorlesung \""+course.title+"\""
    }
    println(courseTitle3(Tutorial(Course("Prog 2"))))
  }
}

// in einer gemeinsamen Sourcecode-Datei
sealed abstract class Lecture
case class Course(title: String) extends Lecture
case class Exercise(belongsTo: Course) extends Lecture
case class Tutorial(belongsTo: Course) extends Lecture