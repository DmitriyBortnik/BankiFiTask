package com.softensity.common

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.ContentTypeRange
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, PredefinedFromStringUnmarshallers, Unmarshaller}
import akka.util.ByteString
import com.softensity.server.Server
import org.json4s.{DefaultFormats, Formats, MappingException, Serialization}

import java.lang.reflect.InvocationTargetException
import scala.collection.immutable.Seq
import scala.concurrent.ExecutionContextExecutor

object BaseControllerProtocol extends Json4sSupport {

  implicit val serialization: Serialization = org.json4s.native.Serialization
  implicit val ec: ExecutionContextExecutor = Server.executionContext
  implicit val formats: Formats = DefaultFormats
}

trait BaseController extends Directives with PredefinedFromStringUnmarshallers {}

trait Json4sSupport {

  def unmarshallerContentTypes: Seq[ContentTypeRange] = List(`application/json`)

  private val jsonStringMarshaller = Marshaller.stringMarshaller(`application/json`)

  private val jsonStringUnmarshaller = Unmarshaller.byteStringUnmarshaller
    .forContentTypes(unmarshallerContentTypes: _*)
    .mapWithCharset {
      case (ByteString.empty, _) => throw Unmarshaller.NoContentException
      case (data, charset)       => data.decodeString(charset.nioCharset.name)
    }

  implicit def unmarshaller[A: Manifest](
                                          implicit serialization: Serialization,
                                          formats: Formats
                                        ): FromEntityUnmarshaller[A] =
    jsonStringUnmarshaller
      .map { s =>
        serialization.read[A](s)
      }
      .recover { _ => _ =>
        {
          case MappingException(_, ite: InvocationTargetException) =>
            throw ite.getCause
        }
      }

  implicit def marshaller[A <: AnyRef](
                                        implicit serialization: Serialization,
                                        formats: Formats
                                      ): ToEntityMarshaller[A] =
    jsonStringMarshaller.compose(serialization.write[A])

}