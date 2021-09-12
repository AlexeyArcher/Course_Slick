package view

import javafx.{concurrent => jfxc}
import scalafx.application.Platform
import scalafx.scene.Node
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label}


class TaskRunner(mainView: Node,
                 glassPane: Node,
                 statusLabel: Label) {
  def run[R](caption: String,
             op: => R): Unit = {

    def showProgress(progressEnabled: Boolean): Unit = {
      mainView.disable = progressEnabled
      glassPane.visible = progressEnabled
    }

    Platform.runLater {
      showProgress(true)
      statusLabel.text = caption
    }

    val task = new jfxc.Task[R] {
      override def call(): R = {
        op
      }
      override def succeeded(): Unit = {
        showProgress(false)
        statusLabel.text = caption + " - Done."
      }
      override def failed(): Unit = {

        showProgress(false)
        statusLabel.text = caption + " - Failed."
        val t = Option(getException)
        t.foreach(_.printStackTrace())
        new Alert(AlertType.Error) {
          initOwner(owner)
          title = caption
          headerText = "Operation failed. " + t.map("Exception: " + _.getClass).getOrElse("")
          contentText = t.map(_.getMessage).getOrElse("")
        }.showAndWait()
      }
      override def cancelled(): Unit = {
        showProgress(false)
        statusLabel.text = caption + " - Cancelled."
      }
    }

    val th = new Thread(task, caption)
    th.setDaemon(true)
    th.start()
  }


}
