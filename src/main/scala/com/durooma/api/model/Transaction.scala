package com.durooma.api.model

/**
  * Created by hannes on 18/03/2017.
  */
case class Transaction(source: Long, target: Long, amount: Double, tags: Array[String])
