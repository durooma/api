package com.durooma.api.route

import akka.http.scaladsl.server.directives.{AuthenticationDirective, Credentials}
import akka.http.scaladsl.server.Directives
import com.durooma.api.model.Session

import scala.concurrent.Future

/**
  * Created by hannes on 24.03.17.
  */
class CustomDirectives extends Directives {

  val authenticateToken: AuthenticationDirective[Session] = authenticateOAuth2Async("durooma", {
    case Credentials.Provided(token) => Session.get(token)
    case _ => Future.successful(None)
  })

}
