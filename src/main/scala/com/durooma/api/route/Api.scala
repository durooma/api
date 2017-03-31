package com.durooma.api.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ExceptionHandler
import com.durooma.api.error.AuthenticationError
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException

/**
  * Created by hannes on 18/03/2017.
  */
object Api extends CustomDirectives with JsonSupport {

  val genericExceptionHandler = ExceptionHandler {
    case AuthenticationError(message) => complete((StatusCodes.Unauthorized, message))
    case e: MySQLIntegrityConstraintViolationException => e.getErrorCode match {
      case 1062 => complete((StatusCodes.BadRequest, e.getMessage))
    }
    case e =>
      e.printStackTrace()
      complete((StatusCodes.InternalServerError, e.getLocalizedMessage))
  }

  val route = handleExceptions(genericExceptionHandler) {
    AccountResource.route ~
    TransactionResource.route ~
    UserResource.route ~
    SessionResource.route
  }

}
