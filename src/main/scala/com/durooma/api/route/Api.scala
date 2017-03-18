package com.durooma.api.route

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.durooma.api.model.Account
import com.durooma.db.Tables
import slick.jdbc.MySQLProfile.api._;

/**
  * Created by hannes on 18/03/2017.
  */
class Api extends Directives with JsonSupport {

  val db = Database.forConfig("data.db")

  implicit val system = ActorSystem("db");
  implicit val materializer = ActorMaterializer();
  implicit val executionContext = system.dispatcher;

  val route = path("hello") {
    get {
      val action = Tables.Account.map(a => (a.name, a.initialBalance)).result
      complete(db.run(action).map(_.map((Account.apply _).tupled(_))))
    }
  }

}
