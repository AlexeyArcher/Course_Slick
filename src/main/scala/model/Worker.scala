package model

import scalafx.beans.property.StringProperty

case class Worker(pass_ : String, sur_ : String, first_ : String, pat_ : String) {
  val pass_id = new StringProperty(this, "pass_id", pass_)
  val surname = new StringProperty(this, "firstName", sur_)
  val name = new StringProperty(this, "lastName", first_)
  val patronym = new StringProperty(this, "patronym", pat_)
  /*pass_id.onChange((_, oldValue, newValue) => None)
  surname.onChange((_, oldValue, newValue) => None)
  name.onChange((_, oldValue, newValue) => None)
  patronym.onChange((_, oldValue, newValue) => None)*/
}
