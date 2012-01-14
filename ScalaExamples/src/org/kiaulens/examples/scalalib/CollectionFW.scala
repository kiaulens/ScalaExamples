package org.kiaulens.examples.scalalib

/**
 * Alle Collection-Klassen befinden sich im Package 'scala.collection.generic'
 * Von den meisten Collections gibt es 3 Versionen:
 * scala.collection, immutable (garantiert unveränderbar), mutable (Methoden zur Zustandsänderung)
 */
object CollectionFWMain {
  def main(args: Array[String]) {
    import scala.collection.immutable.HashMap // muss importiert werden
    import scala.collection.mutable.Map // kann importiert werden, um eine veränderliche Map zu erzeugen
    
    // Instanz kann durch Angabe des Klassennamens und der Elemente in Klammern erzeugt werden,
    // d.h. apply-Methode ist im Companion-Objekt definiert
    Traversable(1,2,3)
    Map("B" -> "Berlin", "S" -> "Stuttgart")
    Set("Haskell", "Scala", "Java")
    List(1,2,3)
    HashMap(1 -> "one", 2 -> "two")
    
    // eine vielzahl von Collection-Methoden sind bereits im Trait Traversable definiert
    // die einzige abstrakte Methode ist:
    // def foreach[U](f: Elem => U): Unit
    // welche eine Funktion 'f' auf alle Elemente der Collection anwendet
    
    // unendliche Collections mit 'lazy' Auswertung, z.B. 'scala.collection.immutable.Stream'
    // Unterscheidung von endlichen- und unendlichen Collections mit 'hasDefiniteSize'
    
    // der Subtrait von 'Traversable', 'Iterable' hat eine abstrakte Methode:
    // def iterator: Iterator[A]
    
    // Sequenzen - der Trait 'Seq'
      // haben einen Index (partielle Abbildung von Int zum Elementtyp)
      val list = List("Hallo","Welt")
      println(list(1))
      
      val linearseq = scala.collection.mutable.LinearSeq("Eins","Zwei","Drei")
      linearseq(1) = "Zwo"
      println(linearseq)
      
      // die Subtraits 'LinearSeq' und 'Vector' unterscheiden sich nur in der Performance-Charakteristika
      // lineare Sequenz hat effiziente 'head'- und 'tail'-Methoden
      // Vektoren haben effiziente 'apply'- und 'length'-Methoden, sowie 'update'-Methoden (veränderbar)
      
      // Buffer (nur veränderbare Version)
        // Elemente können eingefügt, ersetzt und entfernt werden
        // eneue Elemente werden effizient am Ende angefügt
        // zusätzliche Methoden:
          // buf insert (i, x)		// insert element x at index i
          // buf insertAll(i, xs)	// insert all at index i
          // buf remove i			// remove element at index i
          // buf remove (i, n)		// remove n elements starting at i
          // buf -= x				// remove element x
          // buf --= xs				// remove all elements in xs
          // buf += x				// append x
          // buf += (x,y,z)			// append x, y, and z
          // buf ++= xs				// append all in xs
          // x +=: buf				// prepend x
          // xs ++=: buf			// prepend all in xs
        // häufig genutze Implementierungen:
          // scala.collection.mutable.ListBuffer
          // scala.collection.mutable.ArrayBuffer
      // Set - Mengen-Repräsentation
      val set = Set(1,2,3)
      set(1) // true - 1 ist in set enthalten
      set(4) // false - 4 ist nicht in set enthalten
      // Elemente können bei veränderlichen Sets hinzugefügt oder entfernt werden
      set + 4
      set - 1
      val mSet = scala.collection.mutable.Set(1,2,3)
      mSet += 4
      mSet -= 1
      // Mengenoperationen
        val xs = Set(1,2,3,4)
        val ys = Set(3,4,5,6)
        // Schnittmenge
        xs & ys
        println(xs intersect ys)
        // Vereinigungsmenge
        xs | ys
        println(xs union ys)
        // Differenzmenge
        xs &~ ys
        println(xs diff ys)
        // Teilmenge
        println(xs subsetOf ys)
        // leere Menge (nicht Test, ob leere Menge!)
        println(xs.empty)
      // Subtraits von Set:
        // SortedSet - gibt Elemente mit 'foreach' oder 'iterator' in frei wählbarer Reihenfolge zurück
        // BitSet - eine Menge von nicht-negativen ganzen Zahlen, die wenig Speicher verbraucht
      // Map (dritter Subtrait von Iterable)
        // Speichert Schlüssel-Wert-Paare
        val map = Map("Eins" -> "One", "Zwei" -> "Two")
        // 'apply'
        println(map("Zwei"))
        // 'map("Drei")' - wirft eine "key not found" exception
        // 'get' gibt Option zurück
        println(map.get("Zwei"))
        println(map.get("Drei"))
        // Hinzufügen und Entfernen
          // für mutable Maps
          map("Fünf") = "Five"
          map.update("Fünf", "Five")
          map += ("Fünf" -> "Five")
          // für immutable Maps
          map.updated("Fünf", "Five")
          map + ("Fünf" -> "Five")
        // Subtrait 'SortedMap' sortiert nach dem Schlüssel
      // Gleichheit von Collections
        // 3 Kategorien: Sets, Maps, Seqs
        // Zwei Collections aus verschiedenen Kategorien sind immer ungleich
        // Zwei Collections aus derselben Kategorie sind immer dann gleich, wenn dieselben
        // Elemente enthalten sind
        List(1,2,3) == Vector(1,2,3) // true
        List(1,2,3) == Vector(1,3,2) // false
        import scala.collection.immutable.{HashSet,TreeSet}
        HashSet(1,2) == TreeSet(2,1) // true
  }
}