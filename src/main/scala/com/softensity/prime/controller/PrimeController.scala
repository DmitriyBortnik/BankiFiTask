package com.softensity.prime.controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.softensity.common.BaseController
import com.softensity.prime.service.PrimeService

class PrimeController(service: PrimeService) extends BaseController {
  import com.softensity.common.BaseControllerProtocol._

  def getPrimes(number: Int, algorithmOpt: Option[String]): Route =
    get {
      algorithmOpt match {
        case Some("sieve") =>
          complete((StatusCodes.OK, service.sieveNumbers(number)))
        case None =>
          complete((StatusCodes.OK, service.primeNumbers(number)))
        case Some(_) =>
          complete(StatusCodes.BadRequest)
      }
    }

  lazy val route: Route =
    pathPrefix("primes" / IntNumber) { number =>
      parameter("algorithm".as[String] ?) { algorithm =>
        getPrimes(number, algorithm)
      }
    }
}
