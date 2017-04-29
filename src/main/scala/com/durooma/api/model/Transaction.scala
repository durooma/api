package com.durooma.api.model

import java.sql.{Date, Timestamp}

import com.durooma.db.Tables.profile.api._
import com.durooma.db.Tables
import DB._
import com.durooma.db.Tables.LabelRow

import scala.util.Success

/**
  * Created by hannes on 18/03/2017.
  */
case class Transaction(
  id: Long,
  date: Date,
  source: Option[Long],
  target: Option[Long],
  amount: BigDecimal,
  exempt: BigDecimal,
  description: Option[String],
  labels: Seq[LabelRow]
)

case class TransactionBody(
  date: Date,
  source: Option[Long],
  target: Option[Long],
  amount: BigDecimal,
  exempt: BigDecimal,
  description: Option[String],
  labels: Seq[Long]) {

  def row(id: Long = 0)(implicit session: Session) = Tables.TransactionRow(id, date, session.user.id, source, target, amount, exempt, description)

}

object Transaction {

  val fromTuple = (Transaction.apply _).tupled(_)

  def map(q: Query[Tables.Transaction, Tables.Transaction#TableElementType, Seq]) = {
    (q joinLeft Tables.TransactionLabel on (_.id === _.transactionId) joinLeft Tables.Label on (_._2.map(_.labelId) === _.id))
      .map({ case ((t, _), l) =>
        (t.id, t.date, t.source, t.target, t.amount, t.exempt, t.description, l)
      })

    //q.map(t => (t.id, t.date, t.source, t.target, t.amount, t.exempt, t.description))
  }

  def run(q: Query[Tables.Transaction, Tables.Transaction#TableElementType, Seq]) = {
    db.run(map(q).result).map(ts => {
      ts.groupBy(_._1).mapValues(t => Transaction(
        t.head._1,
        t.head._2,
        t.head._3,
        t.head._4,
        t.head._5,
        t.head._6,
        t.head._7,
        t.flatMap(_._8)
      )).values
    })
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

  def addLabels(id: Long, t: TransactionBody)(implicit session: Session) =
    Tables.TransactionLabel ++= t.labels.map(l => Tables.TransactionLabelRow(0, id, l))

  def create(transaction: TransactionBody)(implicit session: Session) = {

    val createTransaction = (Tables.Transaction returning Tables.Transaction.map(_.id)) += transaction.row()

    db.run(
      createTransaction
        .map(id => {
          addLabels(id, transaction)
          id
        })
    ).flatMap(id =>
      db.run(Tables.Label.filter(_.id inSet transaction.labels).result)
        .map(labels => Transaction(
          id,
          transaction.date,
          transaction.source,
          transaction.target,
          transaction.amount,
          transaction.exempt,
          transaction.description,
          labels
        ))
    )

  }

  def update(id: Long, transaction: TransactionBody)(implicit session: Session) = {

    val updateTransaction = Tables.Transaction.filter(t => t.id === id && t.owner === session.user.id).update(transaction.row(id))
    val removeLabels = Tables.TransactionLabel.filter(l => l.transactionId === id).delete

    db.run(
      removeLabels
      >> addLabels(id, transaction)
      >> updateTransaction
    )
  }

  def remove(id: Long)(implicit session: Session) = {
    db.run(Tables.Transaction.filter(a => a.id === id && a.owner === session.user.id).delete)
  }

}