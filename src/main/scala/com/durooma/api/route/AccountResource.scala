package com.durooma.api.route

import akka.http.scaladsl.server.Directives
import com.durooma.api.model.Account

object AccountResource extends Directives with JsonSupport {

  val route = pathPrefix("account") {
    pathEnd {
      get {
        complete(Account.all)
      }
    } ~
      path(LongNumber) { id =>
        get {
          complete(Account.get(id))
        }
      }
  }

}
