package model

import java.time.LocalDate
import scalafx.beans.property.{ObjectProperty, StringProperty}
case class Customer(pass_ : String, sur_ : String, first_ : String, pat_ : String) {
  val pass_id = new StringProperty(this, "pass_id", pass_)
  val surname = new StringProperty(this, "firstName", sur_)
  val name = new StringProperty(this, "lastName", first_)
  val patronym = new StringProperty(this, "patronym", pat_)
}