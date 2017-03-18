package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

object Account {

  def all() = {
    db.run(Tables.Account.result)
  }

}
