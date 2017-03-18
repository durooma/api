package com.durooma.api.model

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.durooma.db.Tables.profile.api._

object DB {

  implicit val db = Database.forConfig("data.db")
  implicit val system = ActorSystem("db");
  implicit val materializer = ActorMaterializer();
  implicit val executionContext = system.dispatcher;

}
