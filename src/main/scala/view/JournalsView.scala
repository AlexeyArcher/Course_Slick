package view

import model.Journal
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font

import java.sql.Date
import java.time.LocalDate
import org.joda.time.{ LocalDate => JodaDate}
class JournalsView(val model: JournalsViewModel) {

  val title = "Journals"

  private val table: TableView[Journal] = {
    // Define columns


    val inColumn = new TableColumn[Journal, JodaDate] {
      text = "arrival Date"
      cellValueFactory = {
        _.value.dateIn

      }


      prefWidth = 150
    }
    val outColumn = new TableColumn[Journal, JodaDate] {
      text = "departure Date"
      cellValueFactory = {
        _.value.dateOut

      }


      prefWidth = 150
    }
    val PassIdColumn = new TableColumn[Journal, Int] {
      text = "pass_id"
      cellValueFactory = {
        _.value.pass_id
      }
      cellFactory = {_: TableColumn[Journal, Int] => new TextFieldTableCell[Journal, Int]()}
      prefWidth = 100
    }

    val numberColumn = new TableColumn[Journal, Int] {
      text = "room number"
      cellValueFactory = {
        _.value.room

      }

      cellFactory = {_: TableColumn[Journal, Int] => new TextFieldTableCell[Journal, Int]()}
      prefWidth = 100
    }

    // Build the table

    new TableView[Journal](model.items) {
      editable = true
      columns ++= Seq(inColumn, outColumn, PassIdColumn, numberColumn)
      margin = Insets(10, 0, 10, 0)


    }

  }
  model.selectedItems = table.selectionModel.value.selectedItems
  private val addButton = new Button {
    text = "Add"
    onAction = _ => model.onAddItem()
  }

  private val removeButton = new Button {
    text = "Remove"
    disable <== !model.canRemoveRow
    onAction = _ => model.onRemove()
  }
  private val queryButton = new Button {
    text = "Query"
    onAction = _ => model.onQuery()
  }

  /*private val resetButton = new Button {
    text = "Reset"
    onAction = _ => model.onReset()
  }*/

  val view: Parent = {

    val label = new Label(title) {
      font = Font("Arial", 20)
    }

    val buttonBar = new ButtonBar {
      buttons = Seq(addButton, removeButton, queryButton)
    }

    new BorderPane {
      top = label
      center = table
      bottom = buttonBar
      padding = Insets(10, 10, 10, 10)
    }
  }

}
