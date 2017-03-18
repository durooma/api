package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables

import scala.concurrent.ExecutionContext

object Account {

  def all(implicit db: Database, executionContext: ExecutionContext) = {
    db.run(Tables.Account.result)
  }

}
