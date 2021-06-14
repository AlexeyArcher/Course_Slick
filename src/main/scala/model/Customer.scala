package model

import java.time.LocalDate
import scalafx.beans.property.{ObjectProperty, StringProperty}
case class Customer(pass_ : String, sur_ : String, first_ : String, pat_ : String,
                    in_ : LocalDate, out_ : LocalDate, num_ : Int) {
  val pass_id = new StringProperty(this, "pass_id", pass_)
  val surname = new StringProperty(this, "firstName", sur_)
  val name = new StringProperty(this, "lastName", first_)
  val patronym = new StringProperty(this, "patronym", pat_)
  val inD = new ObjectProperty[LocalDate](this, "date on in", in_)
  val outD = new ObjectProperty[LocalDate](this, "date on out", out_)
  val number = new ObjectProperty[Int](this, "number of room", num_)
}