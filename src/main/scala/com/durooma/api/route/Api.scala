package com.durooma.api.route

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.durooma.api.model.Account
import com.durooma.db.Tables.profile.api._

/**
  * Created by hannes on 18/03/2017.
  */
class Api extends Directives with JsonSupport {

  implicit val db = Database.forConfig("data.db")
  implicit val system = ActorSystem("db");
  implicit val materializer = ActorMaterializer();
  implicit val executionContext = system.dispatcher;

  val route = path("account") {
    get {
      complete(Account.all)
    }
  }

}
