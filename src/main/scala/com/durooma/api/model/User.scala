package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

case class User(
  id: Long,
  email: String,
  firstName: Option[String],
  lastName: Option[String]
)

object User {

  def all() = {
    db.run(Tables.User.map(u => (u.id, u.email, u.firstName, u.lastName)).result)
      .map(_.map((User.apply _).tupled(_)))
  }

}