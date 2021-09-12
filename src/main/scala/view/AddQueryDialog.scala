package view


import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window
import org.joda.time.{ LocalDate => JodaDate}
import java.time.{LocalDate => Ld}

object AddQueryDialog {
  def showAndWait(parentWindow: Window): Option[(Int, Int, Int)] = {
    val dialog = new Dialog[(Int, Int, Int)]() {

      title = "Query of eff"
      headerText = "Query info"
    }
    implicit def localdatetojoda(local: Ld): JodaDate
    = new JodaDate(local.getYear, local.getMonthValue, local.getDayOfMonth)
    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    val roomField = new TextField()
    val yearField = new TextField()
    val monthField = new TextField()
    dialog.dialogPane().content = new GridPane {
      hgap = 20
      vgap = 20
      padding = Insets(40, 50, 20, 20)
      add(new Label("room:"), 0, 1)
      add(roomField , 1, 1)
      add(new Label("year"), 0, 2)
      add(yearField , 1, 2)
      add(new Label("rr:"), 0, 3)
      add(monthField , 1, 3)
    }

    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)


    okButton.disable <== roomField.text.isEmpty || yearField.text.isEmpty ||
      monthField.text.isEmpty


    Platform.runLater(roomField.requestFocus())


    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        (roomField.getText.toInt, yearField.getText.toInt, monthField.getText.toInt)
      else
        null

    val result = dialog.showAndWait()

    result match {
      case Some((room : Int, year : Int, month: Int)) => Some((room, year, month))
      case _ => None
    }
  }
}