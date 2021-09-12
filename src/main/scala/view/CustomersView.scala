package view

import model.{Customer, Journal}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control.TableColumn.CellDataFeatures
import scalafx.scene.control._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font
import org.joda.time.{ LocalDate => JodaDate}
import java.time.LocalDate
class CustomersView(val model: CustomersViewModel) {

  val title = "Customers"

  private val table: TableView[Customer] = {
    // Define columns

    val passIdColumn = new TableColumn[Customer, Int] {
      text = "pass_id"
      cellValueFactory = {
        _.value.pass_id
      }
      cellFactory = {_: TableColumn[Customer, Int] => new TextFieldTableCell[Customer, Int]()}
      prefWidth = 100
    }
    val surNameColumn = new TableColumn[Customer, String] {
      text = "Surname"
      cellValueFactory = {
        _.value.surname
      }
      cellFactory = TextFieldTableCell.forTableColumn[Customer]()
      prefWidth = 150
    }

    val NameColumn = new TableColumn[Customer, String] {
      text = "name"
      cellValueFactory = {
        _.value.name
      }
      cellFactory = TextFieldTableCell.forTableColumn[Customer]()
      prefWidth = 150
    }

    val patronymColumn = new TableColumn[Customer, String] {
      text = "patronym"
      cellValueFactory = {
        _.value.patronym

      }

      cellFactory = TextFieldTableCell.forTableColumn[Customer]()
      prefWidth = 150
    }

    // Build the table

    new TableView[Customer](model.items) {
      editable = true
      columns ++= Seq(passIdColumn,surNameColumn, NameColumn, patronymColumn)
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

  /*private val resetButton = new Button {
    text = "Reset"
    onAction = _ => model.onReset()
  }*/

  val view: Parent = {

    val label = new Label(title) {
      font = Font("Arial", 20)
    }

    val buttonBar = new ButtonBar {
      buttons = Seq(addButton, removeButton)
    }

    new BorderPane {
      top = label
      center = table
      bottom = buttonBar
      padding = Insets(10, 10, 10, 10)
    }
  }

}
