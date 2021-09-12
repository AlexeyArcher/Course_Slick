package model

import model.JournalDB.Journals
import model.CustomersDB.Customers
import model.RoomsDB.Rooms
import slick.lifted.{ProvenShape, Tag}

import scala.concurrent.{Await, Future}
import slick.jdbc.MySQLProfile.api._

import java.sql.{Date, Timestamp => Times}
import org.joda.time.{LocalDate => JodaDate}
import slick.lifted.MappedToBase.mappedToIsomorphism

import java.time.{Month, Year, YearMonth, LocalDate => Ld}
import scala.concurrent.duration.Duration




object JournalDB {


  class Journals(tag: Tag) extends Table[Journal](tag, "Journals") {

    implicit val localDateToDate = MappedColumnType.base[JodaDate, Date](
      l => new Date(l.toDateTimeAtStartOfDay(org.joda.time.DateTimeZone.UTC).getMillis),
      d => new JodaDate(d.getTime)
    )


    def id: Rep[Option[Int]] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def dateIn: Rep[JodaDate] = column[JodaDate]("dateIn")
    def dateOut: Rep[JodaDate] =  column[JodaDate]("dateOut")
    def pass_id: Rep[Int] = column[Int]("passid of customer")
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
def the_worst_query_in_my_life(room: Int, month: Int, year: Int) = {
  implicit def localdatetojoda(local: Ld): JodaDate
  = new JodaDate(local.getYear, local.getMonthValue, local.getDayOfMonth)
  implicit def jodaTimeMapping: BaseColumnType[JodaDate] = MappedColumnType.base[JodaDate, Times](
    dateTime => new Times(dateTime.toDateTimeAtStartOfDay().getMillis()),
    timeStamp => new JodaDate(timeStamp.getTime)
  )
  val beg: JodaDate = YearMonth.of(year, month).atDay(1)
  val end: JodaDate = YearMonth.of(year, month).atEndOfMonth()


  val q2 = journals.filter {
   p => (p.dateIn >= beg) && (p.dateOut <= end) && (p.room === room)
  }
  run(q2.result)

}
  def the_worst_query_in_my_life2(room: Int, month: Int, year: Int, day: Int): Unit = {
    implicit def localdatetojoda(local: Ld): JodaDate
    = new JodaDate(local.getYear, local.getMonthValue, local.getDayOfMonth)
    implicit def jodaTimeMapping: BaseColumnType[JodaDate] = MappedColumnType.base[JodaDate, Times](
      dateTime => new Times(dateTime.toDateTimeAtStartOfDay().getMillis()),
      timeStamp => new JodaDate(timeStamp.getTime)
    )
    val som = YearMonth.of(year, month)
    journals.filter {
        p => (p.dateIn <= localdatetojoda(som.atDay(day))) && (p.dateOut >= localdatetojoda(som.atDay(day))) && (p.room === room)
      }.length

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