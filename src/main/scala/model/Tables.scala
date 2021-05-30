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
  def pass_id: Rep[Int] = column[Int]("number")
  def surname: Rep[String] = column[String]("positions")
  def name: Rep[String] = column[String]("Price per day")
  def patronym: Rep[String] =  column[String]("vacancy")
  def inDate: Rep[LocalDate] =  column[LocalDate]("Date of arrival")
  def room: Rep[Int] = column[Int]("room of customer")
  override def * : ProvenShape[Customer] = (pass_id, surname, name, patronym, inDate, room) <> (Customer.tupled, Customer.unapply)
}

class Workers(tag: Tag) extends Table[Worker](tag, "Workers") {
  def pass_id: Rep[Int] = column[Int]("number")
  def surname: Rep[String] = column[String]("positions")
  def name: Rep[String] = column[String]("Price per day")
  def patronym: Rep[String] =  column[String]("vacancy")
  override def * : ProvenShape[Worker] = (pass_id, surname, name, patronym) <> (Worker.tupled, Worker.unapply)
}

