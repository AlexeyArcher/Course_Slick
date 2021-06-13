package view

import model.{Room, RoomsDB}
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.stage.Window

class RoomsViewModel {
  var taskRunner: TaskRunner = _

  private val roomsDB = new RoomsDB()

  val parentWindow: ObjectProperty[Window] = ObjectProperty[Window](null.asInstanceOf[Window])
  val items: ObservableBuffer[Room] = new ObservableBuffer[Room]()

  // Read-only collection of rows selected in the table view
  var _selectedItems: ObservableBuffer[Room] = _
  def selectedItems: ObservableBuffer[Room] = _selectedItems
  def selectedItems_=(v: ObservableBuffer[Room]): Unit = {
    _selectedItems = v
    _selectedItems.onChange {
      canRemoveRow.value = selectedItems.nonEmpty
    }
  }

  val canRemoveRow = BooleanProperty(false)

  def setUp(): Unit = {
    items.clear()
    roomsDB.setup()
    items ++= roomsDB.queryRooms()
  }

  def onAddItem(): Unit = {

    val result = AddRoomDialog.showAndWait(parentWindow.value)

    result match {
      case Some(room) =>
        taskRunner.run(
          caption = "Add Room",
          op = {
            // Add new items from database
            roomsDB.add(room)
            // Return items from database
            val updatedItems = roomsDB.queryRooms()
            // Update items on FX thread
            Platform.runLater {
              updateItems(updatedItems)
            }
          }
        )
      case _ =>
    }
  }

  def onRemove(): Unit = {
    taskRunner.run(
      caption = "Remove Selection",
      op = {
        roomsDB.remove(selectedItems.toSeq)
        // Return items from database
        val updatedItems = roomsDB.queryRooms()
        // Update items on FX thread
        Platform.runLater {
          updateItems(updatedItems)
        }
      }
    )
  }

  private def updateItems(updatedItems: Seq[Room]): Unit = {
    val toAdd = updatedItems.diff(items)
    val toRemove = items.diff(updatedItems)
    items ++= toAdd
    items --= toRemove
  }
}
