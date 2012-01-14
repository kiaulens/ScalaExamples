package org.kiaulens.examples.scalalib
import scala.swing._

object GUIMain extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scala-Buch GUI"
    location = new Point(200,200)
    minimumSize = new Dimension(640,480)
    val quitAction = Action("Quit") {System.exit(0)}
    val sayHelloAction = Action("Say Hello") {
      Dialog.showMessage(new FlowPanel,"Hello World")
    }
    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(quitAction)
      }
      contents += new Menu("Misc") {
        contents += new MenuItem(sayHelloAction)
      }
    }
  }
}