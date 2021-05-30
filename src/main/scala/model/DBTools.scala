package model

import slick.jdbc.MySQLProfile.api._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object DBTools {
  private var _initialized = false
  private var _path = ""

  def schemaCreate(path : String): Unit = {
    val db = this.setPath(path).getConnect
    try {
      db.run(this.getTablesQuery).onComplete {
        case Success(data) => this._initialized = true
        case Failure(data) =>
          println(data.getMessage)
          this._initialized = false
        case _ => db.close()
      }
    } catch {
      case e: Exception =>
        println(e.getMessage)
        db.close
    }
  }

  def getConnect: slick.jdbc.MySQLProfile.api.Database = Database.forConfig(this.getPath)

  def setPath(path : String): DBTools.type = {
    this._path = path
    this
  }

  def getPath: String = this._path

  def getTablesQuery: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(
    TableQuery[UsersData].schema.create
  )

  def getInitialization: Boolean = this._initialized
}