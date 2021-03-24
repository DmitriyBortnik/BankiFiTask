package com.softensity.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.softensity.prime.PrimeModule
import com.softensity.search.SearchModule

import scala.concurrent.ExecutionContextExecutor

object Server {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8080

    val route: Route = SearchModule.routes ~ PrimeModule.routes
    val bindingFuture = Http().bindAndHandle(route, host, port)

    println(s"Application is running on $host:$port")
    sys.addShutdownHook({
      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    })

  }

}
