package model

import model.JournalDB.Journals
import model.CustomersDB.Customers
import model.RoomsDB.Rooms
import slick.lifted.{ProvenShape, Tag}

import scala.concurrent.{Await, Future}
import slick.jdbc.MySQLProfile.api._
import java.sql.Date
import scala.concurrent.duration._


object JournalDB {
  class Journals(tag: Tag) extends Table[Journal](tag, "Journals") {
    def id: Rep[Option[Int]] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def dateIn: Rep[Date] = column[Date]("dateIn")
    def dateOut: Rep[Date] =  column[Date]("dateOut")
    def pass_id: Rep[String] = column[String]("passid of customer")
    def room: Rep[Int] = column[Int]("number of room")
    def pass_fk = foreignKey("pass_fk", pass_id, TableQuery[Customers])(_.pass_id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    def room_fk = foreignKey("room_fk", room, TableQuery[Rooms])(_.number, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    override def * : ProvenShape[Journal] = (id, dateIn,dateOut,pass_id, room) <> (Journal.tupled, Journal.unapply)
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
  def queryJournals(): Seq[Journal] = {
    run(journals.result)
  }
  def add(items: Seq[Journal]): Unit = {
    run(journals ++= items)
  }

  def add(p: Journal): Unit = {
    add(Seq(p))
  }
  def remove(items: Seq[Journal]): Unit = {
    val qs = items.map { i =>
      val q = journals.filter { p =>
        p.id === i.id_
      }
      q.delete
    }

    run(DBIO.seq(qs: _*))
  }
  private def run[R](actions: DBIOAction[R, NoStream, Nothing]): R = {
    val db = Database.forConfig("mydb")
    try {
      Await.result(db.run(actions), 2.seconds)
    } finally {
      db.close()
    }
  }
}