package model

import slick.lifted.{ProvenShape, Tag}
import model.WorkersDB.Workers

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object WorkersDB {
  class Workers(tag: Tag) extends Table[Worker](tag, "Workers") {
    def pass_id: Rep[String] = column[String]("pass_id")
    def surname: Rep[String] = column[String]("surname")
    def name: Rep[String] = column[String]("name")
    def patronym: Rep[String] =  column[String]("patronym")
    override def * : ProvenShape[Worker] = (pass_id, surname, name, patronym) <> (Worker.tupled, Worker.unapply)
  }
}


class WorkersDB{
  private val workers: TableQuery[Workers] = TableQuery[Workers]
  val db = Database.forConfig("mydb")
  private val sampleWorker =
    Worker("4715518977", "Kovba", "Alexey", "Michailovich" )
  def setup(): Unit = {
    db.run(workers.schema.create)
  }
  def clear(): Unit = {
    db.run(workers.delete)
  }
  def addSampleContent(): Future[Worker] = {
    db.run(workers returning workers += sampleWorker)
  }
  def queryWorkers(): Future[Seq[Worker]] = {
    db.run(workers.result)
  }
}