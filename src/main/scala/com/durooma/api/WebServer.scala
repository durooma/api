package com.durooma.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.durooma.api.route.Api

/**
  * Created by hannes on 18/03/2017.
  */
object WebServer {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("requests");
    implicit val materializer = ActorMaterializer();
    implicit val executionContext = system.dispatcher;

    Http().bindAndHandle(Api.route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/")

  }

}
