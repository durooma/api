package com.durooma.api.route

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

/**
  * Created by hannes on 24.03.17.
  */
object RouteSystem {

  implicit val system = ActorSystem("requests")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

}
