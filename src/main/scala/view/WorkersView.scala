package view

import model.Worker
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font




class WorkersView(val model: WorkersViewModel) {

  val title = "Workers"

  private val table: TableView[Worker] = {

    val passIdColumn = new TableColumn[Worker, Int] {
      text = "pass_id"
      cellValueFactory = {
        _.value.pass_id
      }
      cellFactory = {_: TableColumn[Worker, Int] => new TextFieldTableCell[Worker, Int]()}
      prefWidth = 100
    }
    val surNameColumn = new TableColumn[Worker, String] {
      text = "Surname"
      cellValueFactory = {
        _.value.surname
      }
      cellFactory = TextFieldTableCell.forTableColumn[Worker]()
      prefWidth = 150
    }

    val NameColumn = new TableColumn[Worker, String] {
      text = "name"
      cellValueFactory = {
        _.value.name
      }
      cellFactory = TextFieldTableCell.forTableColumn[Worker]()
      prefWidth = 150
    }

    val patronymColumn = new TableColumn[Worker, String] {
      text = "patronym"
      cellValueFactory = {
        _.value.patronym

      }
      cellFactory = TextFieldTableCell.forTableColumn[Worker]()
      prefWidth = 150
    }


    new TableView[Worker](model.items) {
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
