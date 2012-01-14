package org.kiaulens.examples.basic;
object Echo extends Application {
  var input = ""
    while(input != "quit") {
      input = readLine(input)
      println(input)
    }
}