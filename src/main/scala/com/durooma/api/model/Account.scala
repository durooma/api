package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

object Account {

  def all() = {
    db.run(Tables.Account.result)
  }

  def get(id: Long) = {
    db.run(Tables.Account.filter(_.id === id).result).map(_.head)
  }

}
