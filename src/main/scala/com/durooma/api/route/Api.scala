package com.durooma.api.route

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.{complete, get, path}
import com.durooma.api.model.Account

/**
  * Created by hannes on 18/03/2017.
  */
class Api extends Directives with JsonSupport {

  val route = path("hello") {
    get {
      complete(Account("test", 0.0))
    }
  }

}
