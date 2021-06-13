package model

import scalafx.beans.property.{IntegerProperty, ObjectProperty, StringProperty}

case class Room(num_ : Int, position_ : Int, value_ : Int, vac_ : Int) {
  val number = new ObjectProperty[Int](this, "number of room", num_)
  val pos = new ObjectProperty[Int](this, "positions in room", position_)
  val price = new ObjectProperty[Int](this, "price per night", value_)
  val vacant = new ObjectProperty[Int](this, "vacant beds", vac_)
}