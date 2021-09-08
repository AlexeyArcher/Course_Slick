package model
import scalafx.beans.property.{ObjectProperty, StringProperty}
import java.sql.Date

/*     def id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def dateIn: Rep[Date] = column[Date]("dateIn")
    def dateOut: Rep[Date] =  column[Date]("dateOut")
    def pass_id: Rep[String] = column[String]("passid of customer")*/
case class Journal(id_ : Option[Int], dateIn_ : Date, dateOut_ : Date, pass_id_ : String, room_ : Int){
  val dateIn = new ObjectProperty[Date](this, "Arrive date", dateIn_)
  val dateOut = new ObjectProperty[Date](this, "Depart date", dateOut_)
  val pass_id = new StringProperty(this, "Pass id", pass_id_)
  val room = new ObjectProperty[Int](this, "Room number", room_)
}
