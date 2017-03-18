package com.durooma.api.model

import com.durooma.db.Tables.AccountRow

/**
  * Created by hannes on 18/03/2017.
  */
case class Transaction(source: AccountRow, target: AccountRow, amount: Double, tags: Array[String])
