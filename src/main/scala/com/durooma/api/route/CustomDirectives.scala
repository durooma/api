package com.durooma.api.route

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import akka.http.scaladsl.server.directives.BasicDirectives.mapRouteResult
import akka.http.scaladsl.server.directives.{AuthenticationDirective, Credentials}
import akka.http.scaladsl.server.{Directive0, Directives}
import com.durooma.api.route.RouteSystem._
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

  def completeDbMutation(result: Future[Int], statusCode: StatusCode = StatusCodes.OK) = complete(result.map({
    case x if x > 0 => statusCode
    case _ => StatusCodes.NotFound
  }))

}
