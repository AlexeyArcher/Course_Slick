package view

import model.Worker
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font




class WorkersView(val model: WorkersViewModel) {

  val title = "Workers"

  private val table: TableView[Worker] = {
    // Define columns

    val passIdColumn = new TableColumn[Worker, String] {
      text = "pass_id"
      cellValueFactory = {
        _.value.pass_id
      }
      prefWidth = 100
    }
    val surNameColumn = new TableColumn[Worker, String] {
      text = "Surname"
      cellValueFactory = {
        _.value.surname
      }
      prefWidth = 150
    }

    val NameColumn = new TableColumn[Worker, String] {
      text = "name"
      cellValueFactory = {
        _.value.name
      }
      prefWidth = 150
    }

    val patronymColumn = new TableColumn[Worker, String] {
      text = "patronym"
      cellValueFactory = {
        _.value.patronym
      }
      prefWidth = 150
    }

    // Build the table
    new TableView[Worker](model.items) {
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

  private val resetButton = new Button {
    text = "Reset"
    onAction = _ => model.onReset()
  }

  val view: Parent = {

    val label = new Label(title) {
      font = Font("Arial", 20)
    }

    val buttonBar = new ButtonBar {
      buttons = Seq(addButton, removeButton, resetButton)
    }

    new BorderPane {
      top = label
      center = table
      bottom = buttonBar
      padding = Insets(10, 10, 10, 10)
    }
  }

}
