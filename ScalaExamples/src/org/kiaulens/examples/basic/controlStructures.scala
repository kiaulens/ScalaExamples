package org.kiaulens.examples.basic;
/**
 * Kontrollstrukturen
 */
abstract class control {
  
  // Vergleiche mit '==' oder equals
  val name = "test"
  name == "test"
  name equals "test"
  name.==("test")
  name.equals("test")
  // soll Objektidentität verglichen werden dann mit 'eq'
  name eq name
  
  // if-Beispiel:
  val args:List[Any]
  println(if (args isEmpty)
    "Hello Stranger!"
  else
    if (args(0) == "Sebastian")
      "Hi"
    else
      "Hello "+args(0))
  
  // while- und do-while-Schleife
  var input = ""
  while(input != "quit") {
    input = readLine()
    println(input)
  }
  
  // for-Scheilfe (gleicht eher einer foreach-Schleife)
  for (arg <- args) println(arg) // durchläuft das 'args'-Array
  
  // for-Schleife mit Zählvariable
  for (i <- 0 to args.length-1) println(args(i))
  // oder
  for (i <- 0.to(args.length-1)) println(args(i)) // to ist eine Methode der Klasse RichInt (Int wird implizit zu RichInt wenn nötig)
  
  // Scope - Eine Funktion mit einer 'verschatteten' Variable
  def fun(x:Int) { // bei fun(3) wird zuerst eine 7, dann eine 3 ausgegeben
    if (x == 3) {
      val x = 7
      println(x)
    }
    println(x)
  }
  
  
}