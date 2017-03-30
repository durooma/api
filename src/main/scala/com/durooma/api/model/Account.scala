package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

case class Account(id: Long, name: String, initialBalance: BigDecimal)
case class AccountBody(name: String, initialBalance: Option[BigDecimal]) {

  def row(id: Long = 0)(implicit session: Session) = Tables.AccountRow(id, session.user.id, name, initialBalance.getOrElse(0.0))

}

object Account {

  val fromTuple = (Account.apply _).tupled(_)

  def map(q: Query[Tables.Account, Tables.Account#TableElementType, Seq]) = q.map(a => (a.id, a.name, a.initialBalance))

  def run(q: Query[Tables.Account, Tables.Account#TableElementType, Seq]) = {
    db.run(map(q).result).map(_.map(fromTuple))
  }

  def all(implicit session: Session) = {
    run(Tables.Account.filter(_.owner === session.user.id))
  }

  def get(id: Long)(implicit session: Session) = {
    run(Tables.Account.filter(a => a.id === id && a.owner === session.user.id)).map(_.headOption)
  }

  def create(account: AccountBody)(implicit session: Session) = {
    db.run((Tables.Account returning Tables.Account.map(_.id) into { (account, id) => Account(id, account.name, account.initialBalance) }) += account.row())
  }

  def update(id: Long, account: AccountBody)(implicit session: Session) = {
    db.run(Tables.Account.filter(a => a.id === id && a.owner === session.user.id).update(account.row(id)))
  }

  def remove(id: Long)(implicit session: Session) = {
    db.run(Tables.Account.filter(a => a.id === id && a.owner === session.user.id).delete)
  }

}
