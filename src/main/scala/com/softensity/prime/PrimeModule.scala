package com.softensity.prime

import akka.http.scaladsl.server.Route
import com.softensity.prime.controller.PrimeController
import com.softensity.prime.service.PrimeService

object PrimeModule {
  private lazy val controller: PrimeController = new PrimeController(service)
  lazy val service = new PrimeService
  lazy val routes: Route = controller.route
}
