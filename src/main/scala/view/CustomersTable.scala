package view


import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Label, ProgressIndicator}
import scalafx.scene.layout.{BorderPane, StackPane, VBox}

class CustomersTable {
  private val customersViewModel = new CustomersViewModel()
  private val customersView = new CustomersView(customersViewModel)
  val glassPane = new VBox {
    children = new ProgressIndicator {
      progress = ProgressIndicator.IndeterminateProgress
      visible = true
    }
    alignment = Pos.Center
    visible = false
  }

  val statusLabel = new Label {
    maxWidth = Double.MaxValue
    padding = Insets(0, 10, 10, 10)
  }
  val rootView = new StackPane {
    children = Seq(
      new BorderPane {
        center = customersView.view
        bottom = statusLabel
      },
      glassPane
    )
  }
  private val taskRunner = new TaskRunner(customersView.view, glassPane, statusLabel)
  customersViewModel.taskRunner = taskRunner
  def run = taskRunner.run(
    caption = "Setup Database",
    op = {
      customersViewModel.setUp()
    }
  )
}
