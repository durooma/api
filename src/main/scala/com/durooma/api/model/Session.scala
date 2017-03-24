package com.durooma.api.model

import com.github.nscala_time.time.Imports._
import java.security.SecureRandom
import java.sql.Timestamp
import java.util.Base64

import com.github.t3hnar.bcrypt._
import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import User._
import DB._
import com.durooma.api.error.AuthenticationError

import scala.concurrent.Future

case class Session(token: String, user: User, expiresAt: DateTime)

case class CustomCredentials(email: String, password: String)

/**
  * Created by hannes on 24.03.17.
  */
object Session {

  private val secureRandom = new SecureRandom()
  private val tokenBuffer = new Array[Byte](64)
  private val expirationPeriod = 30 days

  def generateToken() = {
    secureRandom.nextBytes(tokenBuffer)
    Base64.getEncoder().encodeToString(tokenBuffer)
  }

  def createSession(id: Long) = Tables.SessionRow(0, id, generateToken(), new Timestamp((DateTime.now + expirationPeriod).getMillis()))

  def login(credentials: CustomCredentials): Future[Session] = {
    db.run(Tables.User.filter(_.email === credentials.email).result).map(users => users.headOption).flatMap {
      case None => Future.failed(AuthenticationError("Invalid email or password.")) // unknown email
      case Some(user) => {
        try {
          if (credentials.password.isBcrypted(user.password.get)) {
            val session = createSession(user.id)
            db.run(Tables.Session += session).map { _ => Session(session.token, user, new DateTime(session.expiresAt)) }
          } else {
            Future.failed(AuthenticationError("Invalid email or password.")) // invalid password
          }
        } catch {
          case _ : Throwable => Future.failed(AuthenticationError("Invalid email or password.")) // invalid password
        }
      }
    }
  }

  def logout(implicit session: Session) = {
    db.run(Tables.Session.filter(_.token === session.token).delete).map(_ => "")
  }

  def get(token: String) = {
    db.run(
      (Tables.Session join Tables.User on (_.userId === _.id)).filter(_._1.token === token).result)
      .map(_.headOption)
      .map(_.map { case (sessionRow, userRow) =>
        Session(sessionRow.token, userRow, new DateTime(sessionRow.expiresAt))
      })
  }


}
