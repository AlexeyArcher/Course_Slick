package model

import java.time.LocalDate

case class Customer(pass_id: Int,
                    surname: String,
                    name: String,
                    patronym: Option[String],
                    inDate: LocalDate,
                    room: Int
                   )