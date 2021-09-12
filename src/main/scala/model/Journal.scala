package model
import scalafx.beans.property.{ObjectProperty, StringProperty}
import java.sql.Date
import org.joda.time.LocalDate

case class Journal(id_ : Option[Int], dateIn_ : LocalDate, dateOut_ : LocalDate, pass_id_ : Int, room_ : Int){
  val dateIn = new ObjectProperty[LocalDate](this, "Arrive date", dateIn_)
  val dateOut = new ObjectProperty[LocalDate](this, "Depart date", dateOut_)
  val pass_id = new ObjectProperty[Int](this, "Pass id", pass_id_)
  val room = new ObjectProperty[Int](this, "Room number", room_)
}
