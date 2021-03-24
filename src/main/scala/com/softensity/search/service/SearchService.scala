package com.softensity.search.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.softensity.server.Server

import java.io.StringReader
import java.net.{URLDecoder, URLEncoder}
import scala.concurrent.{ExecutionContextExecutor, Future}

class SearchService(
                     implicit val actorSystem: ActorSystem = Server.system,
                     implicit val executionContext: ExecutionContextExecutor = Server.executionContext
                   ) {

  private val searchUrl = "https://www.google.com/search"
  private val startOfLink = 16
  private val encoding = "UTF-8"

  def search(query: String): Future[String] = {
    val encodedQuery = URLEncoder.encode(query, encoding)
    val request = HttpRequest(uri = searchUrl + "?q=" + encodedQuery)
    for {
      response <- Http().singleRequest(request)
      content <- Unmarshal(response.entity).to[String]
    } yield getSecond(content)
  }

  private def getSecond(content: String): String = {
    val parser = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl().newSAXParser()
    val source = new org.xml.sax.InputSource(new StringReader(content))
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
    val xml = adapter.loadXML(source, parser)
    val nodes = xml \\ "div" \ "div" \ "div"
    val searchResult = nodes.filter(
      div => (div \ "@class" toString) == "kCrYT"
    ) \ "a"
    val link = searchResult(1).toString()
    val endOfLink = link.indexOf("&amp;")
    URLDecoder.decode(link.substring(startOfLink, endOfLink), encoding)
  }

}
