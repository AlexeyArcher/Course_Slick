package model
import slick.lifted.{ProvenShape, Tag}
import model.RoomsDB.Rooms

import scala.concurrent.{Await, Future}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration.Duration




object RoomsDB {
  class Rooms(tag: Tag) extends Table[Room](tag, "Rooms") {
    def number: Rep[Int] = column[Int]("number of room", O.PrimaryKey)
    def pos: Rep[Int] = column[Int]("beds in room")
    def price: Rep[Int] = column[Int]("price per night")
    def vacantbeds: Rep[Int] =  column[Int]("vacant bed")
    override def * : ProvenShape[Room] = (number, pos, price, vacantbeds) <> (Room.tupled, Room.unapply)
  }
}


class RoomsDB{
  private val rooms: TableQuery[Rooms] = TableQuery[Rooms]
  private val sampleRoom =
    Seq(Room(101, 1, 1984, 1))
  def setup(): Unit = {
    run(rooms.schema.createIfNotExists)
  }
  def clear(): Unit = {
    run(rooms.delete)
  }
  def addSampleContent(): Unit = {
    add(sampleRoom)
  }
  def queryRooms(): Seq[Room] = {
    run(rooms.result)
  }
  def add(items: Seq[Room]): Unit = {
    /*val qs = items.map { i =>
      val q = rooms.filter { p =>
        true}
    }*/
    run(rooms ++= items)
  }

  def add(p: Room): Unit = {
    add(Seq(p))
  }
  def remove(items: Seq[Room]): Unit = {
    val qs = items.map { i =>
      val q = rooms.filter { p =>
        p.number === i.num_
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