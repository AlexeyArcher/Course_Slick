package model

import slick.lifted.{ProvenShape, Tag}
import model.WorkersDB.Workers

import scala.concurrent.{Await, Future}
import slick.jdbc.MySQLProfile.api._

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
    Seq(Worker("4715518977", "Kovba", "Alexey", "Michailovich" ))
  def setup(): Unit = {
    db.run(workers.schema.create)
  }
  def clear(): Unit = {
    db.run(workers.delete)
  }
  def addSampleContent(): Unit = {
    add(sampleWorker)
  }
  def queryWorkers(): Seq[Worker] = {
    Await.result(db.run(workers.result), Duration.Inf)
  }
  def add(items: Seq[Worker]): Unit = {
    Await.result(db.run(workers ++= items), Duration.Inf)
  }
  def add(p: Worker): Unit = {
    add(Seq(p))
  }
  def remove(items: Seq[Worker]): Unit = {
    val qs = items.map { i =>
      val q = workers.filter { p =>
        p.pass_id === i.pass
      }
      q.delete
    }

    db.run(DBIO.seq(qs: _*))
  }

}