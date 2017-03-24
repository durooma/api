package com.durooma.api.route

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
          complete(Session.logout)
        }
      }
    }
  }

}
