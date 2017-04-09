package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

/**
  * Created by hannes on 18/03/2017.
  */
case class Transaction(id: Long, source: Option[Long], target: Option[Long], amount: BigDecimal, huquqAmount: BigDecimal)
case class TransactionBody(source: Option[Long], target: Option[Long], amount: BigDecimal, huquqAmount: BigDecimal) {

  def row(id: Long = 0)(implicit session: Session) = Tables.TransactionRow(id, session.user.id, source, target, amount, huquqAmount)

}

object Transaction {

  val fromTuple = (Transaction.apply _).tupled(_)

  def map(q: Query[Tables.Transaction, Tables.Transaction#TableElementType, Seq]) = q.map(t => (t.id, t.source, t.target, t.amount, t.exempt))

  def run(q: Query[Tables.Transaction, Tables.Transaction#TableElementType, Seq]) = {
    db.run(map(q).result).map(_.map(fromTuple))
  }

  def all(transactionType: Option[String] = None)(implicit session: Session) = {
    transactionType match {
      case None => run(Tables.Transaction.filter(_.owner === session.user.id))
      case Some("expense") => expenses
      case Some("income") => income
      case Some("transfer") => transfers
      case x => throw new IllegalArgumentException("Invalid option " + x)
    }
  }

  def expenses(implicit session: Session) = {
    run(Tables.Transaction.filter(t => t.owner === session.user.id && t.target.isEmpty))
  }

  def income(implicit session: Session) = {
    run(Tables.Transaction.filter(t => t.owner === session.user.id && t.source.isEmpty))
  }

  def transfers(implicit session: Session) = {
    run(Tables.Transaction.filter(t => t.owner === session.user.id && t.source.isDefined && t.target.isDefined))
  }

  def get(id: Long)(implicit session: Session) = {
    run(Tables.Transaction.filter(t => t.id === id && t.owner === session.user.id)).map(_.headOption)
  }

  def create(transaction: TransactionBody)(implicit session: Session) = {
    db.run((Tables.Transaction returning Tables.Transaction.map(_.id) into { (t, id) => Transaction(id, t.source, t.target, t.amount, t.exempt) }) += transaction.row())
  }

  def update(id: Long, transaction: TransactionBody)(implicit session: Session) = {
    db.run(Tables.Transaction.filter(t => t.id === id && t.owner === session.user.id).update(transaction.row(id)))
  }

  def remove(id: Long)(implicit session: Session) = {
    db.run(Tables.Transaction.filter(a => a.id === id && a.owner === session.user.id).delete)
  }

}