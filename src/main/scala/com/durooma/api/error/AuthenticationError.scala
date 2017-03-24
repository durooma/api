package com.durooma.api.error

/**
  * Created by hannes on 24.03.17.
  */
case class AuthenticationError(message: String) extends Exception(message)
