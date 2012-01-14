package org.kiaulens.examples.oop
import java.util.Calendar

/**
 * Beispiel: Trait
 * Sind ähnlich zu Interfaces in Java, nur können sie bereits Implementierungen enthalten.
 * Werden in andere Klassen 'hineingemischt'. und können dadurch deren Funktionalität erweitern.
 */
object TAnimalMain {
  def main(args: Array[String]) {
    val fish: Animal_ = new Fish("fish")
    println(fish)
    fish.makeNoise()
  }
}

abstract class Animal_ extends HasAge {
  var name: String
  def makeNoise(): Unit
  override def toString = name+" "+age+" year"+(if(age!=1) "s" else "")+" old"
}

/**
 * Schlüsselwort 'trait' signalisiert ein "Rich Interface".
 * Es wird der, in Scala enthaltene, Trait 'Ordered' implementiert. Dazu wird
 * ledeglich die Funktion compare definiert und ein Objekt this mit that verglichen.
 */
trait HasAge extends Ordered[HasAge] {
  val dateOfBirth: Calendar // Definition des Klassen-Parameters als normales Member
  def age = {
    val today = Calendar.getInstance
    val age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR)
    today.set(Calendar.YEAR, dateOfBirth.get(Calendar.YEAR))
    if (today before dateOfBirth)
      age-1
    else 
      age
  }
  def compare(that: HasAge) = this.age compare that.age
}

class Fish(var name: String, val dateOfBirth: Calendar = Calendar.getInstance /* today */) extends Animal_ {
  def makeNoise() {
    println("blub, blub")
  }
}