package com.durooma.api.route

import akka.http.scaladsl.model.StatusCodes
import com.durooma.api.model.{Transaction, TransactionBody}

object TransactionResource extends CustomDirectives with JsonSupport {

  val route = pathPrefix("transaction") {
    authenticateToken { implicit session =>
      pathEnd {
        get {
          parameters('type.?) { t =>
            complete(Transaction.all(t))
          }
        } ~
        post {
          entity(as[TransactionBody]) { transaction =>
            complete((StatusCodes.Created, Transaction.create(transaction)))
          }
        }
      } ~
      path(LongNumber) { id =>
        get {
          complete(Transaction.get(id))
        } ~
        put {
          entity(as[TransactionBody]) { transaction =>
            completeDbMutation(Transaction.update(id, transaction))
          }
        } ~
        delete {
          completeDbMutation(Transaction.remove(id), StatusCodes.NoContent)
        }
      }
    }
  }

}
