package view

import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control.{Button, ButtonBar, Label, TableColumn, TableView}
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font
import model.Worker
import scalafx.collections.ObservableBuffer


class WorkersView(val model: WorkersViewModel) {

  val title = "Workers"

  private val table: TableView[Worker] = {
    // Define columns


    val surNameColumn = new TableColumn[Worker, String] {
      text = "Surname"
      cellValueFactory = {
        _.value.surname
      }
      prefWidth = 180
    }

    val NameColumn = new TableColumn[Worker, String] {
      text = "name"
      cellValueFactory = {
        _.value.name
      }
      prefWidth = 180
    }

    val patronymColumn = new TableColumn[Worker, String] {
      text = "patronym"
      cellValueFactory = {
        _.value.patronym
      }
      prefWidth = 200
    }

    // Build the table
    new TableView[Worker](model.items) {
      columns ++= Seq(surNameColumn, NameColumn, patronymColumn)
      margin = Insets(10, 0, 10, 0)
    }
  }
  //model.selectedItems = table.selectionModel.value.selectedItems

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
