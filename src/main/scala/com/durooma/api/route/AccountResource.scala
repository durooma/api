package com.durooma.api.route

import akka.http.scaladsl.server.Directives
import com.durooma.api.model.Account

object AccountResource extends Directives with JsonSupport {

  val route = path("account") {
    get {
      complete(Account.all)
    }
  }

}
