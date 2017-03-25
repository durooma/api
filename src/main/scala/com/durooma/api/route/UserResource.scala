package com.durooma.api.route

import akka.http.scaladsl.model.StatusCodes
import com.durooma.api.route.RouteSystem._
import com.durooma.api.model.{User, UserRegistration}

object UserResource extends CustomDirectives with JsonSupport {

  val route = pathPrefix("user") {
    pathEnd {
      post {
        entity(as[UserRegistration]) { user =>
          complete(User.create(user).map(_ => StatusCodes.Created))
        }
      }
    } ~
    path(LongNumber) { id =>
      get {
        complete(User.get(id))
      }
    }
  }

}
