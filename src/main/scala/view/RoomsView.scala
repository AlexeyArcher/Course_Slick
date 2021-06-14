package view
import model.Room
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control.TableColumn.CellDataFeatures
import scalafx.scene.control._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font
import scalafx.util.converter.DefaultStringConverter



class RoomsView(val model: RoomsViewModel){
  val title = "Rooms"

  private val table: TableView[Room] = {
    // Define columns

    val numberColumn = new TableColumn[Room, Int] {
      text = "number"
      cellValueFactory = {
        _.value.number
      }

      cellFactory = {_: TableColumn[Room, Int] => new TextFieldTableCell[Room, Int]()}
      prefWidth = 100
    }
    val posColumn = new TableColumn[Room, Int] {
      text = "positions"
      cellValueFactory = {
        _.value.pos
      }
      cellFactory = {_: TableColumn[Room, Int] => new TextFieldTableCell[Room, Int]()}
      prefWidth = 150
    }

    val PriceColumn = new TableColumn[Room, Int] {
      text = "price p night"
      cellValueFactory = {
        _.value.price
      }
      cellFactory = {_: TableColumn[Room, Int] => new TextFieldTableCell[Room, Int]()}
      prefWidth = 150
    }

    val vacantColumn = new TableColumn[Room, Int] {
      text = "vacantBeds"
      cellValueFactory = {
        _.value.vacant

      }
      cellFactory = {_: TableColumn[Room, Int] => new TextFieldTableCell[Room, Int]()}
      prefWidth = 150
    }

    // Build the table

    new TableView[Room](model.items) {
      editable = true
      columns ++= Seq(numberColumn, posColumn, PriceColumn, vacantColumn)
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
