package view

import model.{Customer, CustomersDB}
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.stage.Window

class CustomersViewModel {

  var taskRunner: TaskRunner = _

  private val customersDB = new CustomersDB()

  val parentWindow: ObjectProperty[Window] = ObjectProperty[Window](null.asInstanceOf[Window])
  val items: ObservableBuffer[Customer] = new ObservableBuffer[Customer]()

  // Read-only collection of rows selected in the table view
  var _selectedItems: ObservableBuffer[Customer] = _
  def selectedItems: ObservableBuffer[Customer] = _selectedItems
  def selectedItems_=(v: ObservableBuffer[Customer]): Unit = {
    _selectedItems = v
    _selectedItems.onChange {
      canRemoveRow.value = selectedItems.nonEmpty
    }
  }

  val canRemoveRow = BooleanProperty(false)

  def setUp(): Unit = {
    items.clear()
    customersDB.setup()
    items ++= customersDB.queryCustomers()
  }

  def onAddItem(): Unit = {

    val result = AddCustomerDialog.showAndWait(parentWindow.value)

    result match {
      case Some(customer) =>
        taskRunner.run(
          caption = "Add Customer",
          op = {
            // Add new items from database
            customersDB.add(customer)
            // Return items from database
            val updatedItems = customersDB.queryCustomers()
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
        customersDB.remove(selectedItems.toSeq)
        // Return items from database
        val updatedItems = customersDB.queryCustomers()
        // Update items on FX thread
        Platform.runLater {
          updateItems(updatedItems)
        }
      }
    )
  }

  /*  def onReset(): Unit = {
      taskRunner.run(
        caption = "Reset DB",
        op = {
          CustomersDB.clear()
          //CustomersDB.addSampleContent()
          // Return items from database
          val updatedItems = CustomersDB.queryCustomers()
          // Update items on FX thread
          Platform.runLater {
            updateItems(updatedItems)
          }
        }
      )
    }
  */
  private def updateItems(updatedItems: Seq[Customer]): Unit = {
    val toAdd = updatedItems.diff(items)
    val toRemove = items.diff(updatedItems)
    items ++= toAdd
    items --= toRemove
  }
}
