package com.durooma.api.route

import akka.http.scaladsl.model.StatusCodes
import com.durooma.api.route.RouteSystem._
import com.durooma.api.model.{CustomCredentials, Session}

object SessionResource extends CustomDirectives with JsonSupport {

  val route = pathPrefix("session") {
    pathEnd {
      post {
        entity(as[CustomCredentials]) { credentials =>
          complete(Session.login(credentials))
        }
      } ~
      delete {
        authenticateToken { implicit session =>
          complete(Session.logout.map(_ => StatusCodes.NoContent))
        }
      }
    }
  }

}
