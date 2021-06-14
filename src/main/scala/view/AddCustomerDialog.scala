package view


import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window
import model.Customer
import scala.language.implicitConversions

object AddCustomerDialog {
  def showAndWait(parentWindow: Window): Option[Customer] = {
    // Create the custom dialog.
    val dialog = new Dialog[Customer]() {
      initOwner(parentWindow)
      title = "Add New Customer"
      headerText = "Customer Info"
    }

    // Set the button types.
    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    // Create the username and password labels and fields.
    val PassIdTextField = new TextField()
    val SurnameTextField = new TextField()
    val NameField = new TextField()
    val patronymField = new TextField()
    val dateInField = new DatePicker()
    val dateOutField = new DatePicker()
    val numberField = new TextField()
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
      add(new Label("Date of arrival:"), 0, 4)
      add(dateInField , 1, 4)
      add(new Label("Date of departure:"), 0, 5)
      add(dateOutField , 1, 5)
      add(new Label("Number:"), 0, 6)
      add(numberField , 1, 6)
    }

    // Enable/Disable OK button depending on whether all data was entered.
    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)
    // Simple validation that sufficient data was entered
    okButton.disable <== (PassIdTextField.text.isEmpty ||
      SurnameTextField.text.isEmpty || NameField.text.isEmpty ||  numberField.text.isEmpty)

    // Request focus on the first name field by default.
    Platform.runLater(PassIdTextField.requestFocus())

    // When the OK button is clicked, convert the result to a Person.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        Customer(PassIdTextField.text() , SurnameTextField.text(), NameField.text(), patronymField.text(), dateInField.value.value, dateOutField.value.value, numberField.text().toInt)
      else
        null

    val result = dialog.showAndWait()

    // Clean up result type
    result match {
      case Some(Customer(id,first,last,patronym, dIn, dO, number)) => Some(Customer(id,first,last,patronym, dIn, dO, number))
      case _ => None
    }
  }
}
