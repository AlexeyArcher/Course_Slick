package view
import model.{Worker, WorkersDB}
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.stage.Window


class WorkersViewModel {

  var taskRunner: TaskRunner = _

  private val workersDB = new WorkersDB()

  val parentWindow: ObjectProperty[Window] = ObjectProperty[Window](null.asInstanceOf[Window])
  val items: ObservableBuffer[Worker] = new ObservableBuffer[Worker]()

  var _selectedItems: ObservableBuffer[Worker] = _
  def selectedItems: ObservableBuffer[Worker] = _selectedItems
  def selectedItems_=(v: ObservableBuffer[Worker]): Unit = {
    _selectedItems = v
    _selectedItems.onChange {
      canRemoveRow.value = selectedItems.nonEmpty
    }
  }

  val canRemoveRow = BooleanProperty(false)

  def setUp(): Unit = {
    items.clear()
    workersDB.setup()
    items ++= workersDB.queryWorkers()
  }

  def onAddItem(): Unit = {

    val result = AddWorkerDialog.showAndWait(parentWindow.value)

    result match {
      case Some(worker) =>
        taskRunner.run(
          caption = "Add Worker",
          op = {
            workersDB.add(worker)
            val updatedItems = workersDB.queryWorkers()
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
        workersDB.remove(selectedItems.toSeq)
        val updatedItems = workersDB.queryWorkers()
        Platform.runLater {
          updateItems(updatedItems)
        }
      }
    )
  }


  private def updateItems(updatedItems: Seq[Worker]): Unit = {
    val toAdd = updatedItems.diff(items)
    val toRemove = items.diff(updatedItems)
    items ++= toAdd
    items --= toRemove
  }
}
