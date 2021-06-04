import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label, ProgressIndicator}
import scalafx.scene.layout.{BorderPane, StackPane, VBox}
import view.{WorkersView,WorkersViewModel, TaskRunner}

object Main extends JFXApp {

  // Catch unhandled exceptions on FX Application thread
  Thread.currentThread().setUncaughtExceptionHandler(
    (_: Thread, ex: Throwable) => {
      ex.printStackTrace()
      new Alert(AlertType.Error) {
        initOwner(owner)
        title = "Unhandled exception"
        headerText = "Exception: " + ex.getClass + ""
        contentText = Option(ex.getMessage).getOrElse("")
      }.showAndWait()
    }
  )

  // Create application components
  private val workersViewModel = new WorkersViewModel()
  private val workersView = new WorkersView(workersViewModel)

  private val glassPane = new VBox {
    children = new ProgressIndicator {
      progress = ProgressIndicator.IndeterminateProgress
      visible = true
    }
    alignment = Pos.Center
    visible = false
  }

  private val statusLabel = new Label {
    maxWidth = Double.MaxValue
    padding = Insets(0, 10, 10, 10)
  }

  private val rootView = new StackPane {
    children = Seq(
      new BorderPane {
        center = workersView.view
        bottom = statusLabel
      },
      glassPane
    )
  }

  stage = new JFXApp.PrimaryStage {
    title = workersView.title
    scene = new Scene(rootView)
  }

  private val taskRunner = new TaskRunner(workersView.view, glassPane, statusLabel)
  workersViewModel.taskRunner = taskRunner


  // Initialize database on a separate thread
  taskRunner.run(
    caption = "Setup Database",
    op = {
      workersViewModel.setUp()
    }
  )
}
