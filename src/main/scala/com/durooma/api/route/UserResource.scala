package com.durooma.api.route

import akka.http.scaladsl.server.Directives
import com.durooma.api.model.User

object UserResource extends Directives with JsonSupport {

  val route = pathPrefix("user") {
    pathEnd {
      get {
        complete(User.all)
      }
    } ~
      path(LongNumber) { id =>
        get {
          complete(User.get(id))
        }
      }
  }

}
