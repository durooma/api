package com.durooma.api.model

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._

/**
  * Created by hannes on 18/03/2017.
  */
case class Transaction(id: Long, source: Option[Long], target: Option[Long], amount: BigDecimal, huquqAmount: BigDecimal)

object Transaction {

  val fromTuple = (Transaction.apply _).tupled(_)

  def map(q: Query[Tables.Transaction, Tables.Transaction#TableElementType, Seq]) = q.map(t => (t.id, t.source, t.target, t.amount, t.huquqAmount))

  def run(q: Query[Tables.Transaction, Tables.Transaction#TableElementType, Seq]) = {
    db.run(map(q).result).map(_.map(fromTuple))
  }

  def all(implicit session: Session) = {
    run(Tables.Transaction.filter(_.owner === session.user.id))
  }

}