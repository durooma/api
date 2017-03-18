package com.durooma.api.route

import akka.http.scaladsl.server.Directives

/**
  * Created by hannes on 18/03/2017.
  */
object Api extends Directives with JsonSupport {

  val route = AccountResource.route ~ UserResource.route

}
