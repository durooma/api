package com.durooma.api.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ExceptionHandler
import com.durooma.api.error.AuthenticationError

/**
  * Created by hannes on 18/03/2017.
  */
object Api extends CustomDirectives with JsonSupport {

  val genericExceptionHandler = ExceptionHandler {
    case AuthenticationError(message) => complete((StatusCodes.Unauthorized, message))
  }

  val route = handleExceptions(genericExceptionHandler) {
    AccountResource.route ~ UserResource.route ~ SessionResource.route
  }

}
