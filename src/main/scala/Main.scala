import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label, ProgressIndicator, Tab, TabPane}
import scalafx.scene.layout.{BorderPane, StackPane, VBox}
import view.{CustomersTable, RoomsTable, TaskRunner, WorkersTable, WorkersView, WorkersViewModel}

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
  private val WorkersTable = new WorkersTable
  WorkersTable.run
  private val RoomsTable = new RoomsTable
  RoomsTable.run
  private val CustomersTable = new CustomersTable
  CustomersTable.run
  val SomeTables = new TabPane
  val tab1 = new Tab{
    closable = false
    text = "Workers"
  }
  val tab2 = new Tab{
    closable = false
    text = "Rooms"
  }
  val tab3 = new Tab{
    closable = false
    text = "Customers"
  }
  tab1.content = WorkersTable.rootView
  tab2.content = RoomsTable.rootView
  tab3.content = CustomersTable.rootView
  SomeTables.tabs = List(tab1, tab2, tab3)
  stage = new JFXApp.PrimaryStage {
    title = "David Hilbert named Gorshock Hotel"
    scene = new Scene(SomeTables)
  }

}
