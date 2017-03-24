package com.durooma.api.route

import com.durooma.api.model.Account

object AccountResource extends CustomDirectives with JsonSupport {

  val route = pathPrefix("account") {
    authenticateToken { implicit session =>
      pathEnd {
        get {
          complete(Account.all)
        }
      } ~
      path(LongNumber) { id =>
        get {
          complete(Account.get(id))
        }
      }
    }
  }

}
