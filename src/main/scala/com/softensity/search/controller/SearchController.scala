package com.softensity.search.controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.softensity.common.BaseController
import com.softensity.search.service.SearchService

class SearchController(service: SearchService) extends BaseController {
  import com.softensity.common.BaseControllerProtocol._

  def getSearchResult(query: String): Route = {
    get {
      onSuccess(service.search(query)) { response =>
        complete((StatusCodes.OK, response))
      }
    }
  }

  lazy val route: Route =
    pathPrefix("search") {
      parameter("q".as[String]) { query =>
        getSearchResult(query)
      }
    }

}
