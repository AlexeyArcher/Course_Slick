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
  private val sampleWorker =
    Seq(Worker("4715518977", "Kovba", "Alexey", "Michailovich"))
  def setup(): Unit = {
    run(workers.schema.createIfNotExists)
  }
  def clear(): Unit = {
    run(workers.delete)
  }
  def addSampleContent(): Unit = {
    add(sampleWorker)
  }
  def queryWorkers(): Seq[Worker] = {
    run(workers.result)
  }
  def add(items: Seq[Worker]): Unit = {
    val qs = items.map { i =>
      val q = workers.filter { p =>
        p.pass_id === i.pass
      }.exists
    }
    run(workers ++= items)
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

    run(DBIO.seq(qs: _*))
  }
  private def run[R](actions: DBIOAction[R, NoStream, Nothing]): R = {
    val db = Database.forConfig("mydb")
    try {
      Await.result(db.run(actions), Duration.Inf)
    } finally {
      db.close()
    }
  }
}