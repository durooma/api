package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

case class User(
  id: Long,
  email: String,
  login: String,
  firstName: Option[String],
  lastName: Option[String]
)

object User {

  val fromTuple = (User.apply _).tupled(_)

  def run(q: Query[Tables.User, Tables.User#TableElementType, Seq]) = {
    db.run(q.map(u => (u.id, u.email, u.login, u.firstName, u.lastName)).result).map(_.map(fromTuple))
  }

  def all() = run(Tables.User)

  def get(id: Long) = run(Tables.User.filter(_.id === id)).map(_.head)

}