package model

import scalafx.beans.property.StringProperty

case class Worker(pass: String, sur: String, first: String, pat: String) {
  val pass_id = new StringProperty(this, "pass_id", pass)
  val surname = new StringProperty(this, "firstName", sur)
  val name = new StringProperty(this, "lastName", first)
  val patronym = new StringProperty(this, "emailAddress", pat)
}
