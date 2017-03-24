package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

object Account {

  def all(implicit session: Session) = {
    db.run(Tables.Account.filter(_.owner === session.user.id).result)
  }

  def get(id: Long) = {
    db.run(Tables.Account.filter(_.id === id).result).map(_.head)
  }

}
