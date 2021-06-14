package model
/*
import model.JournalDB.Journals
import slick.lifted.{ProvenShape, Tag}

import scala.concurrent.{Await, Future}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration.Duration


object JournalDB {
  class Journals(tag: Tag) extends Table[Journal](tag, "Journals") {
    def pass_id: Rep[Seq[String]] = column[Seq[String]]("pass_id", O.PrimaryKey)
    def surname: Rep[String] = column[String]("surname")
    def name: Rep[String] = column[String]("name")
    def patronym: Rep[String] =  column[String]("patronym")
    override def * : ProvenShape[Journal] = (pass_id, surname, name, patronym) <> (Worker.tupled, Worker.unapply)
  }
}


class JournalDB{
  private val journals: TableQuery[Journals] = TableQuery[Journals]
  def setup(): Unit = {
    run(journals.schema.createIfNotExists)
  }
  def clear(): Unit = {
    run(journals.delete)
  }
  def queryWorkers(): Seq[Journal] = {
    run(journals.result)
  }
  def add(items: Seq[Journal]): Unit = {
    run(journals ++= items)
  }
  def add(p: Journal): Unit = {
    add(Seq(p))
  }

  private def run[R](actions: DBIOAction[R, NoStream, Nothing]): R = {
    val db = Database.forConfig("mydb")
    try {
      Await.result(db.run(actions), Duration.Inf)
    } finally {
      db.close()
    }
  }
}*/