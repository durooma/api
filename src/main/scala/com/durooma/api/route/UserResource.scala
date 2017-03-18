package com.durooma.api.route

import akka.http.scaladsl.server.Directives
import com.durooma.api.model.User

object UserResource extends Directives with JsonSupport {

  val route = path("user") {
    get {
      complete(User.all)
    }
  }

}
