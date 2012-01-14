package org.kiaulens.examples.oop
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Abstrakte Klassen beinhalten mindestens eine abstrakte Methode oder Feld.
 * Abstrakt heißt, Methoden oder Felder werden ledeglich deklariert, nicht aber initialisiert.
 * Eine abstrakte Klasse kann nicht mit 'new' erzeugt werden. Sie dient der Generalisierung spezieller
 * Subklassen, welche die Funktionalität erst vollständig implementieren.
 */
object AbstractMain {
  def main(args: Array[String]) {
    val lassie: Animal = new Dog("Lassie", new GregorianCalendar(1999, Calendar.APRIL,12))
    val hugo: Animal = new Dog("Hugo")
    // Definition einer anonymen Klasse, die Animal erweitert ('trixi' hat den typ Animal)
    val trixi = new Animal(Calendar.getInstance) {
      var name = "Trixi"
      def makeNoise() {
        println("chirp","chirp")
      }
    }
    println(lassie)
    println(hugo)
    trixi.makeNoise()
  }
}

abstract class Animal(val dateOfBirth: Calendar) {
  var name: String
  def makeNoise(): Unit
  def age = {
    val today = Calendar.getInstance
    val age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR)
    today.set(Calendar.YEAR, dateOfBirth.get(Calendar.YEAR))
    if (today before dateOfBirth)
    age-1
      else 
    age
  }
  override def toString = name+" "+age+" year"+(if(age!=1) "s" else "")+" old"
}

class Dog(var name: String, dateOfBirth: Calendar = Calendar.getInstance /* today */) extends Animal(dateOfBirth) {
  def makeNoise() {
    println("woof, woof")
  }
}

class Cat (var name: String, dateOfBirth: Calendar = Calendar.getInstance /* today */) extends Animal(dateOfBirth) {
  def makeNoise() {
    println("miaow")
  }
}

class Cow (var name: String, dateOfBirth: Calendar = Calendar.getInstance /* today */) extends Animal(dateOfBirth) {
  def makeNoise() {
    println("moo")
  }
}

class Abstract {
  
}