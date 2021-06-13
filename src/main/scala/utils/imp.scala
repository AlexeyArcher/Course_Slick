package utils

import scalafx.beans.property.{BooleanProperty, DoubleProperty, IntegerProperty, LongProperty, StringProperty}
import scala.language.implicitConversions

object imp {

  implicit def stringToProperty(v: String): StringProperty = StringProperty(v)

  implicit def booleanToProperty(v: Boolean): BooleanProperty = BooleanProperty(v)

  implicit def integerToProperty(v: Int): IntegerProperty = IntegerProperty(v)

  implicit def longToProperty(v: Long): LongProperty = LongProperty(v)

  implicit def doubleToProperty(v: Double): DoubleProperty = DoubleProperty(v)

//  implicit def ToPropertry(v: IntegerProperty) = v
}
