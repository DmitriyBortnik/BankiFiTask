package com.softensity.search

import com.softensity.search.service.SearchService
import org.scalatest.funsuite.AsyncFunSuite

import java.net.URL

class SearchServiceTest extends AsyncFunSuite {
  val service: SearchService = SearchModule.service

  test("SearchService.search") {
    service.search("any search query") map { res =>
      assert(new URL(res).toString == res)
    }
  }
}
