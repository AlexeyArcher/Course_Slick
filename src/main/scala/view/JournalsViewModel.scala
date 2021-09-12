package view

import model.{Journal, JournalDB}
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert
import scalafx.stage.{Stage, Window}
import scalafx.scene.control.Alert.AlertType
class JournalsViewModel {

  var taskRunner: TaskRunner = _

  private val journalDB = new JournalDB()

  val parentWindow: ObjectProperty[Window] = ObjectProperty[Window](null.asInstanceOf[Window])

  val items: ObservableBuffer[Journal] = new ObservableBuffer[Journal]()
  // Read-only collection of rows selected in the table view
  var _selectedItems: ObservableBuffer[Journal] = _
  def selectedItems: ObservableBuffer[Journal] = _selectedItems
  def selectedItems_=(v: ObservableBuffer[Journal]): Unit = {
    _selectedItems = v
    _selectedItems.onChange {
      canRemoveRow.value = selectedItems.nonEmpty
    }
  }

  val canRemoveRow = BooleanProperty(false)

  def setUp(): Unit = {
    items.clear()
    journalDB.setup()
    items ++= journalDB.queryJournals()
  }

  def onAddItem(): Unit = {

    val result = AddJournalDialog.showAndWait(parentWindow.value)

    result match {
      case Some(journal) =>
        taskRunner.run(
          caption = "Add Journal",
          op = {
            // Add new items from database
            journalDB.add(journal)
            // Return items from database
            val updatedItems = journalDB.queryJournals()
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
        journalDB.remove(selectedItems.toSeq)
        // Return items from database
        val updatedItems = journalDB.queryJournals()
        // Update items on FX thread
        Platform.runLater {
          updateItems(updatedItems)
        }
      }
    )
  }
  def onQuery(): Unit = {

    val result = AddQueryDialog.showAndWait(parentWindow.value)

    result match {
      case Some((room: Int, year: Int, month: Int)) =>
        taskRunner.run(
          caption = "fiil bill",
          op = {
            // Add new items from database
            // Return items from database
            val updatedItems = journalDB.queryJournals()
            // Update items on FX thread
            Platform.runLater {
              val dialog = new Alert(AlertType.Information) {
                title = "Query result"
                headerText = ""
                contentText = journalDB.the_worst_query_in_my_life(room, year, month).mkString

              }
              dialog.showAndWait()
            }
          }
        )
      case _ =>
    }
  }

  /*  def onReset(): Unit = {
      taskRunner.run(
        caption = "Reset DB",
        op = {
          JournalDB.clear()
          //JournalDB.addSampleContent()
          // Return items from database
          val updatedItems = JournalDB.queryJournal()
          // Update items on FX thread
          Platform.runLater {
            updateItems(updatedItems)
          }
        }
      )
    }
  */
  private def updateItems(updatedItems: Seq[Journal]): Unit = {
    val toAdd = updatedItems.diff(items)
    val toRemove = items.diff(updatedItems)
    items ++= toAdd
    items --= toRemove
  }
}
