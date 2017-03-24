package com.durooma.api

import akka.http.scaladsl.Http
import com.durooma.api.route.Api
import com.durooma.api.route.RouteSystem._

/**
  * Created by hannes on 18/03/2017.
  */
object WebServer {

  def main(args: Array[String]): Unit = {

    Http().bindAndHandle(Api.route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/")

  }

}
