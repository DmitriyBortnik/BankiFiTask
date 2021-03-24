package com.softensity.search

import akka.http.scaladsl.server.Route
import com.softensity.search.controller.SearchController
import com.softensity.search.service.SearchService

object SearchModule {
  private lazy val controller: SearchController = new SearchController(service)
  lazy val service = new SearchService()
  lazy val routes: Route = controller.route
}
