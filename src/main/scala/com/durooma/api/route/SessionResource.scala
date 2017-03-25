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
      authenticateToken { implicit session =>
        get {
          complete(session)
        } ~
        delete {
          complete(Session.logout.map(_ => StatusCodes.NoContent))
        }
      }
    }
  }

}
