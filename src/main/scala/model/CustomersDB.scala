package model

import slick.lifted.{ProvenShape, Tag}
import model.CustomersDB.Customers
import java.time.LocalDate

import scala.concurrent.{Await, Future}
import slick.jdbc.MySQLProfile.api._
import java.sql.Date
import scala.concurrent.duration.Duration


object CustomersDB {
  implicit val localDateColumnType = MappedColumnType.base[LocalDate, Date](
    d => Date.valueOf(d),
    d => d.toLocalDate
  )
  class Customers(tag: Tag) extends Table[Customer](tag, "Customers") {
    def pass_id: Rep[String] = column[String]("pass_id", O.PrimaryKey)
    def surname: Rep[String] = column[String]("surname")
    def name: Rep[String] = column[String]("name")
    def patronym: Rep[String] =  column[String]("patronym")
    def inD = column[LocalDate]("inD")
    def outD = column[LocalDate]("outD")
    def number: Rep[Int] = column[Int]("number")
    override def * : ProvenShape[Customer] = (pass_id, surname, name, patronym,inD, outD, number) <> (Customer.tupled, Customer.unapply)
  }
}


class CustomersDB{
  private val customers: TableQuery[Customers] = TableQuery[Customers]
  private val sampleCustomer =
    Seq(Customer("4715518977", "Harrington", "Billy", "Jesus",
      LocalDate.parse("1969-07-14"), LocalDate.parse("2018-03-02"),69))
  def setup(): Unit = {
    run(customers.schema.createIfNotExists)
  }
  def clear(): Unit = {
    run(customers.delete)
  }
  def addSampleContent(): Unit = {
    add(sampleCustomer)
  }
  def queryCustomers(): Seq[Customer] = {
    run(customers.result)
  }
  def add(items: Seq[Customer]): Unit = {
    val qs = items.map { i =>
      val q = customers.filter { p =>
        p.pass_id === i.pass_
      }.exists
    }
    run(customers ++= items)
  }
  def add(p: Customer): Unit = {
    add(Seq(p))
  }
  def remove(items: Seq[Customer]): Unit = {
    val qs = items.map { i =>
      val q = customers.filter { p =>
        p.pass_id === i.pass_
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