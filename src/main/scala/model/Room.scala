package model

import scalafx.beans.property.ObjectProperty

case class Room(num_ : Int, position_ : Int, value_ : Int) {
  val number = new ObjectProperty[Int](this, "number of room", num_)
  val pos = new ObjectProperty[Int](this, "positions in room", position_)
  val price = new ObjectProperty[Int](this, "price per night", value_)
}