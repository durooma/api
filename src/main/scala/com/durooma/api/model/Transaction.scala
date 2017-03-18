package com.durooma.api.model

/**
  * Created by hannes on 18/03/2017.
  */
case class Transaction(source: Account, target: Account, amount: Double, tags: Array[String])
