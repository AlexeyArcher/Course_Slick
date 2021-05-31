package model

import scala.concurrent.Future
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.MySQLProfile.api._
import java.time.LocalDate

class Rooms(tag: Tag) extends Table[Room](tag, "Rooms") {
  def number: Rep[Int] = column[Int]("number", O.PrimaryKey)
  def pos: Rep[Int] = column[Int]("positions")
  def price: Rep[Int] = column[Int]("Price per day")
  def vacant: Rep[Boolean] =  column[Boolean]("vacancy")
  override def * : ProvenShape[Room] = (number, pos, price, vacant) <> (Room.tupled, Room.unapply)
}

class Customers(tag: Tag) extends Table[Customer](tag, "Customers") {
  def pass_id: Rep[Int] = column[Int]("PassPort_id")
  def surname: Rep[String] = column[String]("Surname")
  def name: Rep[String] = column[String]("Name")
  def patronym: Rep[Option[String]] =  column[Option[String]]("Patronym(optional)", O.Default(None))
  def inDate: Rep[LocalDate] =  column[LocalDate]("Date of arrival")
  def room: Rep[Int] = column[Int]("room of customer")
  override def * : ProvenShape[Customer] = (pass_id, surname, name, patronym, inDate, room) <> (Customer.tupled, Customer.unapply)
}

class Workers(tag: Tag) extends Table[Worker](tag, "Workers") {
  def pass_id: Rep[Int] = column[Int]("number")
  def surname: Rep[String] = column[String]("positions")
  def name: Rep[String] = column[String]("Price per day")
  def patronym: Rep[Option[String]] =  column[Option[String]]("vacancy")
  override def * : ProvenShape[Worker] = (pass_id, surname, name, patronym) <> (Worker.tupled, Worker.unapply)
}

object Rooms {
  val table = TableQuery[Rooms]
}

class RoomsRep(db: Database) {
  val table = TableQuery[Rooms]

  def create(room: Room): Future[Room] = {
    db.run(table returning table += room)
  }

  def update(room: Room): Future[Int] = {
    db.run(table.filter(_.number === room.number).update(room))
  }

  def delete(room: Room): Future[Int] = {
    db.run(table.filter(_.number === room.number).delete)
  }
}
object Customers {
  val table = TableQuery[Customers]
}

class CustomersRep(db: Database) {
  val table = TableQuery[Customers]

  def create(customer: Customer): Future[Customer] = {
    db.run(table returning table += customer)
  }

  def update(customer: Customer): Future[Int] = {
    db.run(table.filter(_.pass_id === customer.pass_id).update(customer))
  }

  def delete(customer: Customer): Future[Int] = {
    db.run(table.filter(_.pass_id === customer.pass_id).delete)
  }
}
object Workers {
  val table = TableQuery[Workers]
}

class WorkersRep(db: Database) {
  val table = TableQuery[Workers]

  def create(worker: Worker): Future[Worker] = {
    db.run(table returning table += worker)
  }

  def update(worker: Worker): Future[Int] = {
    db.run(table.filter(_.pass_id === worker.pass_id).update(worker))
  }

  def delete(worker: Worker): Future[Int] = {
    db.run(table.filter(_.pass_id === worker.pass_id).delete)
  }
}