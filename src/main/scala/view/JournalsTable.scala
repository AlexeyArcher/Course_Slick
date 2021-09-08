package view


import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Label, ProgressIndicator}
import scalafx.scene.layout.{BorderPane, StackPane, VBox}

class JournalsTable {
  private val journalsViewModel = new JournalsViewModel()
  private val journalsView = new JournalsView(journalsViewModel)
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
        center = journalsView.view
        bottom = statusLabel
      },
      glassPane
    )
  }
  private val taskRunner = new TaskRunner(journalsView.view, glassPane, statusLabel)
  journalsViewModel.taskRunner = taskRunner
  def run = taskRunner.run(
    caption = "Setup Database",
    op = {
      journalsViewModel.setUp()
    }
  )
}
