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
    // Create the custom dialog.
    val dialog = new Dialog[Worker]() {
      initOwner(parentWindow)
      title = "Add New Worker"
      headerText = "Worker Info"
    }

    // Set the button types.
    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    // Create the username and password labels and fields.
    val PassIdTextField = new TextField()
    val SurnameTextField = new TextField()
    val NameField = new TextField()
    val patronymField = new TextField()
    dialog.dialogPane().content = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)

      add(new Label("Pass Id:"), 0, 0)
      add(PassIdTextField , 1, 0)
      add(new Label("Surname:"), 0, 1)
      add(SurnameTextField, 1, 1)
      add(new Label("Name:"), 0, 2)
      add(NameField , 1, 2)
      add(new Label("Patronym:"), 0, 0)
      add(patronymField , 1, 3)
    }

    // Enable/Disable OK button depending on whether all data was entered.
    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)
    // Simple validation that sufficient data was entered
    okButton.disable <== (PassIdTextField.text.isEmpty ||
      SurnameTextField.text.isEmpty || NameField.text.isEmpty)

    // Request focus on the first name field by default.
    Platform.runLater(PassIdTextField.requestFocus())

    // When the OK button is clicked, convert the result to a Person.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        Worker(PassIdTextField.text() , SurnameTextField.text(), NameField.text(), patronymField.text())
      else
        null

    val result = dialog.showAndWait()

    // Clean up result type
    result match {
      case Some(Worker(id,first,last,patronym)) => Some(Worker(id,first,last,patronym))
      case _ => None
    }
  }
}
