package view


import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window
import model.Worker


object AddWorkerDialog {
  def showAndWait(parentWindow: Window): Option[Worker] = {
    val dialog = new Dialog[Worker]() {
      initOwner(parentWindow)
      title = "Add New Worker"
      headerText = "Worker Info"
    }

    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    val PassIdTextField = new TextField()
    val SurnameTextField = new TextField()
    val NameField = new TextField()
    val patronymField = new TextField()
    dialog.dialogPane().content = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(50, 100, 10, 10)

      add(new Label("Pass Id:"), 0, 0)
      add(PassIdTextField , 1, 0)
      add(new Label("Surname:"), 0, 1)
      add(SurnameTextField, 1, 1)
      add(new Label("Name:"), 0, 2)
      add(NameField , 1, 2)
      add(new Label("Patronym:"), 0, 3)
      add(patronymField , 1, 3)
    }

    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)
    okButton.disable <== (PassIdTextField.text.isEmpty ||
      SurnameTextField.text.isEmpty || NameField.text.isEmpty)

    Platform.runLater(PassIdTextField.requestFocus())

    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        Worker(PassIdTextField.text().toInt , SurnameTextField.text(), NameField.text(), patronymField.text())
      else
        null

    val result = dialog.showAndWait()

    result match {
      case Some(Worker(id,first,last,patronym)) => Some(Worker(id,first,last,patronym))
      case _ => None
    }
  }
}
