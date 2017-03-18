package com.durooma.api.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.durooma.api.model.{Account, Transaction}
import spray.json.DefaultJsonProtocol

/**
  * Created by hannes on 18/03/2017.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val accountFormat = jsonFormat2(Account)
  implicit val transactionFormat = jsonFormat4(Transaction)
}