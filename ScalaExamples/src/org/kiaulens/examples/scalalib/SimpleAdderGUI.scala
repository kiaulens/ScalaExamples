package org.kiaulens.examples.scalalib
import scala.swing._
import scala.swing.event._

object SimpleAdderGUI extends SimpleSwingApplication {
  val numberA = new TextField {
    text = "1"
    columns = 5
  }
  val numberB = new TextField {
    text = "2"
    columns = 5
  }
  val result = new Label("3")
  listenTo(numberA, numberB)
  reactions += {
    case EditDone(_) => result.text = (numberA.text.toInt + numberB.text.toInt).toString
  }
  def top = new MainFrame {
    title = "Simple Adder"
    minimumSize = new Dimension(250,20)
    contents = new FlowPanel(numberA, new Label(" + "), numberB, new Label(" = "), result)
  }
}