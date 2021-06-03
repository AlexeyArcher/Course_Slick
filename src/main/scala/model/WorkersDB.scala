package model

import slick.lifted.{ProvenShape, Tag}
import model.WorkersDB.Workers

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object WorkersDB {
  class Workers(tag: Tag) extends Table[Worker](tag, "Workers") {
    def pass_id: Rep[String] = column[String]("number")
    def surname: Rep[String] = column[String]("positions")
    def name: Rep[String] = column[String]("Price per day")
    def patronym: Rep[Option[String]] =  column[Option[String]]("vacancy")
    override def * : ProvenShape[Worker] = (pass_id, surname, name, patronym) <> (Worker.tupled, Worker.unapply)
  }
}


class WorkersDB{
  private val workers: TableQuery[Workers] = TableQuery[Workers]
  val db = Database.forConfig("mydb")
  private val sampleWorkers = Seq(
    Worker( "4715518977", "Kovba", "Alexey", Some("Michailovich"), )
  )
  def setup(): Unit = {
    db.run(workers.schema.create)
  }
  def clear(): Unit = {
    db.run(workers.delete)
  }
  def addSampleContent(): Unit = {
    db.add(sampleWorkers)
  }
  def queryWorkers(): Future[Seq[workers]] = {
    db.run(workers.result)
  }
}