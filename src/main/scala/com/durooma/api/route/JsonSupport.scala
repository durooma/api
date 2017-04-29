package com.durooma.api.route

import java.sql.Date

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.durooma.api.model._
import com.durooma.db.Tables
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat}

/**
  * Created by hannes on 18/03/2017.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object DateJsonFormat extends RootJsonFormat[DateTime] {

    private val parserISO : DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()

    override def write(obj: DateTime) = JsString(parserISO.print(obj))

    override def read(json: JsValue) : DateTime = json match {
      case JsString(s) => parserISO.parseDateTime(s)
      case _ => throw DeserializationException("Invalid date format: " + json)
    }
  }

  implicit object SqlDateJsonFormat extends RootJsonFormat[Date] {

    override def write(obj: Date) = JsString(obj.toString)
    override def read(json: JsValue) = json match {
      case JsString(s) => Date.valueOf(s)
      case _ => throw DeserializationException("Invalid date format: " + json)
    }

  }

  implicit val userFormat = jsonFormat5(User.apply)
  implicit val userRegistrationFormat = jsonFormat5(UserRegistration.apply)
  implicit val accountFormat = jsonFormat4(Account.apply)
  implicit val accounBodyFormat = jsonFormat2(AccountBody.apply)
  implicit val labelFormat = jsonFormat3(Tables.LabelRow.apply)
  implicit val transactionFormat = jsonFormat8(Transaction.apply)
  implicit val transactionBodyFormat = jsonFormat7(TransactionBody.apply)
  implicit val sessionFormat = jsonFormat3(Session.apply)
  implicit val credentialsFormat = jsonFormat2(CustomCredentials.apply)

}