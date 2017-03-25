package com.durooma.api.route

import akka.http.scaladsl.model.StatusCodes
import com.durooma.api.model.{Account, AccountBody}

object AccountResource extends CustomDirectives with JsonSupport {

  val route = pathPrefix("account") {
    authenticateToken { implicit session =>
      pathEnd {
        get {
          complete(Account.all)
        } ~
        post {
          entity(as[AccountBody]) { account =>
            complete((StatusCodes.Created, Account.create(account)))
          }
        }
      } ~
      path(LongNumber) { id =>
        get {
          complete(Account.get(id))
        } ~
        put {
          entity(as[AccountBody]) { account =>
            completeDbMutation(Account.update(id, account))
          }
        } ~
        delete {
          completeDbMutation(Account.remove(id), StatusCodes.NoContent)
        }
      }
    }
  }

}
