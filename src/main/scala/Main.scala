
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Tab, TabPane}
import view.{CustomersTable, JournalsTable, RoomsTable, WorkersTable}

object Main extends JFXApp3 {

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
  override def start(): Unit = {

    val WorkersTable = new WorkersTable
    WorkersTable.run
    val RoomsTable = new RoomsTable
    RoomsTable.run
    val CustomersTable = new CustomersTable
    CustomersTable.run
    val JorunalsTable = new JournalsTable
    JorunalsTable.run
    val SomeTables = new TabPane
    val tab1 = new Tab {
      closable = false
      text = "Workers"
    }
    val tab2 = new Tab {
      closable = false
      text = "Rooms"
    }
    val tab3 = new Tab {
      closable = false
      text = "Customers"
    }
    val tab4 = new Tab {
      closable = false
      text = "Journals"
    }
    tab1.content = WorkersTable.rootView
    tab2.content = RoomsTable.rootView
    tab3.content = CustomersTable.rootView
    tab4.content = JorunalsTable.rootView
    SomeTables.tabs = List(tab1, tab2, tab3, tab4)
    stage = new JFXApp3.PrimaryStage {
      title = "David Hilbert named Gorshock Hotel"
      scene = new Scene(SomeTables)
    }
  }
}
