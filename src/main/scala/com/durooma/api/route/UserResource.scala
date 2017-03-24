package com.durooma.api.route

import com.durooma.api.model.User

object UserResource extends CustomDirectives with JsonSupport {

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
