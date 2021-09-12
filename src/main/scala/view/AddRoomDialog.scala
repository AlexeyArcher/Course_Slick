package view


import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window
import model.Room


object AddRoomDialog {
  def showAndWait(parentWindow: Window): Option[Room] = {
    // Create the custom dialog.
    val dialog = new Dialog[Room]() {
      initOwner(parentWindow)
      title = "Add New Room"
      headerText = "Room Info"
    }

    // Set the button types.
    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    // Create the username and password labels and fields.
    val NumTextField = new TextField()
    val PosTextField = new TextField()
    val PriceTextField = new TextField()
    dialog.dialogPane().content = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(50, 100, 10, 10)

      add(new Label("Number of Room:"), 0, 0)
      add(NumTextField , 1, 0)
      add(new Label("Positions in Room:"), 0, 1)
      add(PosTextField, 1, 1)
      add(new Label("price per night"), 0, 2)
      add(PriceTextField , 1, 2)
    }

    // Enable/Disable OK button depending on whether all data was entered.
    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)
    // Simple validation that sufficient data was entered
    okButton.disable <== (NumTextField.text.isEmpty ||
      PosTextField.text.isEmpty || PriceTextField.text.isEmpty)

    // Request focus on the first name field by default.
    Platform.runLater(NumTextField.requestFocus())

    // When the OK button is clicked, convert the result to a Person.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        Room(NumTextField.text().toInt , PosTextField.text().toInt, PriceTextField.text().toInt)
      else
        null

    val result = dialog.showAndWait()

    // Clean up result type
    result match {
      case Some(Room(num, pos, price)) => Some(Room(num, pos, price))
      case _ => None
    }
  }
}
