package com.durooma.api.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.durooma.api.model.Transaction
import com.durooma.db.Tables.AccountRow
import spray.json.DefaultJsonProtocol

/**
  * Created by hannes on 18/03/2017.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val accountFormat = jsonFormat3(AccountRow.apply)
  implicit val transactionFormat = jsonFormat4(Transaction.apply)
}