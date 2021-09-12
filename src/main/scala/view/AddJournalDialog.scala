package view


import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window
import model.Journal
import scalafx.beans.property.BooleanProperty
import org.joda.time.{ LocalDate => JodaDate}
import java.time.{LocalDate => Ld}
import java.sql.Date

object AddJournalDialog {
  def showAndWait(parentWindow: Window): Option[Journal] = {
    // Create the custom dialog.
    val dialog = new Dialog[Journal]() {
      initOwner(parentWindow)
      title = "Add New Order"
      headerText = "Order Info"
    }
    implicit def localdatetojoda(local: Ld): JodaDate
    = new JodaDate(local.getYear, local.getMonthValue, local.getDayOfMonth)
    // Set the button types.
    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    // Create the username and password labels and fields.
    val dateInField = new DatePicker()
    val dateOutField = new DatePicker()
    val PassIdTextField = new TextField()
    val roomField = new TextField()
    dialog.dialogPane().content = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(50, 100, 10, 10)

      add(new Label("Date of arrival:"), 0, 1)
      add(dateInField , 1, 1)
      add(new Label("Date of departure:"), 0, 2)
      add(dateOutField , 1, 2)
      add(new Label("PassIdOfMan:"), 0, 3)
      add(PassIdTextField , 1, 3)
      add(new Label("Number:"), 0, 4)
      add(roomField , 1, 4)
    }

    // Enable/Disable OK button depending on whether all data was entered.
    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)
    // Simple validation that sufficient data was entered

    okButton.disable <== PassIdTextField.text.isEmpty ||
      roomField.text.isEmpty

    // Request focus on the first name field by default.
    Platform.runLater(PassIdTextField.requestFocus())

    // When the OK button is clicked, convert the result to a Person.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        Journal(None, dateInField.getValue, dateOutField.getValue, PassIdTextField.getText.toInt, roomField.getText.toInt)
      else
        null

    val result = dialog.showAndWait()

    // Clean up result type
    result match {
      case Some(Journal(id, dateIn, dateOut, pass_id, room)) => Some(Journal(id, dateIn, dateOut, pass_id, room))
      case _ => None
    }
  }
}
