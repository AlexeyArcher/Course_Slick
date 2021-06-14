package model
import scalafx.beans.property.{ObjectProperty, StringProperty}

import java.time.LocalDate

case class Journal(num_ : Int, month_ : String, year_ : String, sett_ : Int, days_ : Int){
  val number = new ObjectProperty[Int](this, "number of room", num_)
  val mon =  new StringProperty(this, "month", month_)
  val year =  new StringProperty(this, "year", year_)
  val setl = new ObjectProperty[Int](this, "numbers of customers", sett_)
  val days = new ObjectProperty[Int](this, "days are busy", days_)
}
