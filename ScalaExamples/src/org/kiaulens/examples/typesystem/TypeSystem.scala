package org.kiaulens.examples.typesystem

/**
 * Scala ist statisch typisiert. Der Typ muss jedoch nicht immer
 * angegeben werden, auf Grund der Typinferenz.
 */
object TypeSystemMain {
  def main(args: Array[String]) {
    // Typhierarchie
      // 'Any' -> 'AnyVal', 'AnyRef'
    
      // 'AnyVal' (primitive Scala-Typen) -> 'Boolean', 'Byte', 'Short', 'Char', 'Int',
      // 'Long', 'Float', 'Double', 'Unit'
    
      // 'AnyRef' ist Basistyp aller selbst definierten und alles anderen Typen (entspricht java.lang.object in Java)
      // 'ScalaObject' steht zwischen 'AnyRef' und den Referenztypen um Scala-Klassen von Java-Klassen zu unterscheiden
    
      // 'bottom types'
        // ('Null' mit dem Wert 'null') <- <Subtyp> <- 'AnyRef'
        // 'Nothing' <- <Subtyp> <- 'AnyRef', 'AnyVal'
    
      // 'Option' drückt die Möglichkeit der Nichtexistenz eines gesuchten Wertes aus
        // 'None' zeigt einen nicht vorhandenen Wert an
        // 'Some(x)' bettet den gefundenen Wert 'x' in den 'Option'-Typ ein
        // Vorteil: bereits am Typ der Funktion ist zu erkennen, ob ein Ergebnis optional ist
    
    // Parametrischer Polymorphismus und Varianz
      // 'parametrische Datentypen' ('generische Typen' in Java) (auch als 'type constructor' bezeichnet)
        // sind Datentypen die mit einem Typparameter definiert werden
        // bei einem konreten Wert, wird der Typparameter durch einen Typ instanziert
        // z.B. eine Liste 'List[T]' mit dem Typ 'T'
        val list = List(1,2,3) // 'list: List[Int]' (konkreter Typ)
        
        // Typparameter und auch der konkrete Typ stehen in eckigen Klammern
        // mehr als ein Typparameter werden durch Kommata getrennt, z.B. 'Map[A,B]'
        // Typkonstruktoren mit zwei Typparametern können Infix geschrieben werden (Map[A,B] wird
        // zu 'A Map B')
        type ->[A,B] = Map[A,B] // ein Typsynonym für Map
        val m: Int -> String = Map(0 -> "", 1 -> "")
        
        // Funktionen mit Typvariablen, z.B.:
        def mkList[A](xs: A*) = xs.toList
        mkList() // List[Nothing]
        mkList(1,2,3) // List[Int]
        mkList("one","two","three") // List[java.lang.String]
        mkList(1,2,"Hello",1.1,(1,2)) // List[Any]
        mkList(1,2.0) // List[Double] - impliziete Umwandlung des Ints nach Double
        mkList[AnyVal](1,2.0) // List[AnyVal] - um den Int beizubehalten
        
        // 'nonvariant' - generische Typen sind in Scala standardmäßig starr
        def g(x: MyClass[AnyRef]) = x // funkioniert nur mit Typ AnyRef
        val myAnyRef = new MyClass[AnyRef]
        val myString = new MyClass[String]
        println(myAnyRef)
        println(myString)
        println(g(myAnyRef))
        // 'g(myString)' - funktioniert nicht, obwohl der Typ String Subtyp von Typ AnyRef ist
        
        // 'kovariant' - flexibler Datentyp
        def g2(x: MyClass2[AnyRef]) = x
        val myString2 = new MyClass2[String]
        println(g2(myString2)) // funktioniert
        // viele Scala-Collections sind kovariant, z.B. 'List', 'Seq', 'Queue'
        // Map[A, +B] ist für die Schlüssel nonvariant und für die Werte kovariant
        
        // 'Kontravarianz'
        def g3(x: MyClass3[AnyRef]) = x
        val myString3 = new MyClass3[String]
        val myAny = new MyClass3[Any]
        // 'g3(myString3)' - funktioniert auch hier nicht, aber
        println(g3(myAny))// funktioniert
        // Beispiel für Kontravarianz: 'Function'-Traits
        trait Function1[-T1, +R]
        def h(p: Person) = p
        h(new Woman)
        // 'h(new AnyRef)' - funktioniert nicht
        h(new Woman): AnyRef
        // 'h(new Woman): Woman' - funktioniert nicht
        
        // Ko- und Kontra-Varianz sind nur für Klassen mit unveränderlichen Feldern zulässig
        
        // Upper und Lower Bounds - Einschränken zulässiger Typparameter
        val listOfPersons = new ListOfPersons(new Woman, new Man)
        val listOfWoman = new ListOfPersons(new Woman, new Woman)
        val listOfMan = new ListOfPersons(new Man, new Man)
        // Upper Bounds und Traits
        def sort[T <: Ordered[T]](xs: List[T]): List[T] = xs // akzeptiert jeden Typ 'T' der den Trait Ordered hineingemixt hat
        // Lower Bounds - Beispiel 'enqueue' erwartet ein Typ 'B', für den gilt: 'B' ist Supertyp von 'A' (kontravariant)
        // def enqueue[B >: A](elem: B) = ...
        
        // Angabe von Upper und Lower Bound für einen Typ
        // 'x: C' ist definiert für Typen für welche gilt: 'C' ist Supertyp von 'A' und 'C' ist Subtyp von 'B'
          /* class MyClass[A,B] {
               def doit[C :> A <: B](x: C) = ...
             }*/
        
        // Views und View Bounds - impliziete Konversion von einem Typ in einen anderen (View)
        
        // def maxList[T <: Ordered[T]](elems: List[T]): T = ...
        
        // Bei einem 'View' muss der Elementtyp den Ordered-Trait nicht implementieren, sondern
        // eine impliziete Konversion von T nach Ordered[T] muss verfügbar sein,
        // Die Definition sieht dann wie folgt aus:
        // def maxList[T](elems: List[T]) (implicit converter: T => Ordered[T]): T = ...
        // oder kürzer:
        // def maxList[T <% Ordered[T]](elems: List[T]): T = ...
        // 'T <% Ordered[T]' ist ein 'view bound' und sagt aus: T ist bereits ein Ordered[T] oder
        // kann impliziet in einen solchen umgewandelt werden.
        
        // Context Bounds
        // z.B. 'T: Ordered'
        // eine Konversion von 'T' nach 'Ordered[T]' muss nicht verfügbar sein, sondern
        // es muss nur ein Wert vom Typ 'Ordered[T]' geben.
        
        // Arrays und @spezialized
        // Scala nutzt generische Arrays im Gegensatz zu Java
        // durch das Manifest kann zur Laufzeit festgestellt werden um welchen Array-Typ es sich handelt
        def listToArray[T: ClassManifest](list: List[T]) = {
          val xs = new Array[T](list.length)
          for (i <- 0 until list.length) xs(i) = list(i)
          xs
        }
        // Funktionen die selbst kein Array erzeugen, aber eine Funktion aufrufen, die dies macht
        // benötigen auch ein 'ClassManifest'
        def mkArray[T: ClassManifest](x: T*) = listToArray(x.toList)
        
        // Generalized Type Constraints (Klasse: siehe weiter unten)
        val string = new Foo("Hello World!")
        val int = new Foo(1234)
        val float = new Foo(1.23F)
        println(string stringLength)
        // 'int stringLength' - funktioniert bereits zur kompilierzeit nicht
        println(int addIntToInt 1)
        println(int addDoubleToNumber 4.56)
        println(float addDoubleToNumber 4.56)
        
        // Self-Type-Annotation
        // der Typ des Wertes 'this' kann explizit deklariert werden oder ein Synonym für 'this' definiert werden
        val a = new A
        a.printIt("Hello")
        a.b.c.printItA("Hello")
        a.b.c.printItB("Hello")
        
        val birdie = new Bird
        val lassie = new Dog
        birdie.run
        lassie.run
        
        // Strukturelle und existenzielle Typen (zur Klasse siehe unten)
        RunWithTime.runWithTime(new RunMe)
        
        val matchOnCollection: AnyRef => Unit = { // existenzielle Typen und Pattern Matching
          case _: List[_]	=> println("Eine Liste")
          case _: Map[_,_]	=> println("Eine Map")
          case _			=> println("Etwas anderes")
        }
        // existenzielle Typen sind dazu gedacht Javas Wildcard und Raw Types aus Scala heraus zu nutzen
        // z.B. in Java 'Iterator<?>' (ein Iterator irgendeines Typs) wird in Scala zu Iterator[_]
        // Analog dazu der Java-Typ 'Iterator<? extends Component>' in Scala:
        // 'Iterator[_ <: Component]'
  }
}

class MyClass[T] // Klasse mit nonvariantem generischen Datentyp
// Klassen mit Varianz-Annotationen (+,-)
class MyClass2[+T] // Klasse mit kovariantem generischen Datentyp
class MyClass3[-T] // Klasse mit kontravariantem generischen Datentypen

abstract class Person
class Woman extends Person
class Man extends Person
/**
 * Klasse zur Verwaltung von Personenlisten.
 * Der Typparameter wird mit einem Upper Bound auf die Klasse Person und davon
 * abgeleitete Klassen beschränkt.
 */
class ListOfPersons[T <: Person](persons: T*) {
  val list = persons.toList
  override def toString = list.toString
}
/**
 * Mit der Annotation '@specialized' kann der Compiler spezielle Versionen
 * für die primitiven Datentypen generieren.
 */
class Vector[@specialized A] {
  /* def apply(i: Int): A = //...
    def map[@specialized(Int, Boolean) B] (f: A => B) = // ...
  */
}
/**
 * Generalized Type Constraints
 * Möglichkeit der weiteren Einschränkung von Typparametern:
 * A =:= B	Typ 'A' muss 'B' sein
 * A <:< B	'A' muss Subtyp von 'B' sein (analog zu <:)
 * A <%< B	'A' muss sich in 'B' umwandeln lassen (analog zu <%)
 */
class Foo[A](a: A) {
  def stringLength(implicit evidence: A =:= String) = a.length
  def addIntToInt(x: Int)(implicit evidence: A <:< Int): Int = a + x
  def addDoubleToNumber(x: Double)(implicit evidence: A <%< Double): Double = a + x
}
/**
 * Self-Type-Annotation
 * Die Annoation wird unmittelbar nach der öffnenden geschweiften Klammer einer Klasse
 * oder eines Traits geschrieben und einem folgenden Doppelpfeil =>.
 */
class A { selfA =>
  def printIt(message: String) = println("A.printIt: "+message)
  class B { selfB =>
    def printIt(message: String) = selfA.printIt("B.printIt: "+message)
    class C {
      def printItA(message: String) = selfA.printIt("C.printItA: "+message)
      def printItB(message: String) = selfB.printIt("C.printItB: "+message)
    }
    val c = new C
  }
  val b = new B
}
trait Noise {
  def makeNoise: Unit
}
trait Food {
  def eat: Unit
}
/**
 * Statt den Trait 'Animal' durch 'trait Animal extends Noise with Food' zu definieren,
 * wird nur der Typ entsprechend angegeben. Die beiden Methoden 'makeNoise' und 'eat'
 * sind dann wie bei 'extends' in 'Animal' nutzbar.
 */
trait Animal {
  self: Noise with Food =>
  def run = {
    makeNoise
    eat
    makeNoise
  }
}
trait Bark extends Noise {
  def makeNoise = println("Woof, woof!")
}
trait Gorge extends Food {
  def eat = println("Gorge, Gorge!")
}
trait Tweet extends Noise {
  def makeNoise = println("Chirp, chirp!")
}
trait Pick extends Food {
  def eat = println("Pick, pick!")
}
class Dog extends Animal with Bark with Gorge
class Bird extends Animal with Tweet with Pick
/**
 * 'structural type'
 * der Strukturelle Typ 'Something' besagt das es ein beliebiger Typ sein kann, aber
 * die Methode 'run' für den Typ definiert sein muss.
 */
object RunWithTime {
  type Something = { def run() }
  def runWithTime(f: Something) {
    import scala.compat.Platform.currentTime
    val executionStart: Long = currentTime
    f.run
    val total = currentTime - executionStart
    println("[total "+total+"ms]")
  }
}
class RunMe {
  def run() {
    println("Starting")
    Thread.sleep(1000)
    println("Stopping")
  }
}