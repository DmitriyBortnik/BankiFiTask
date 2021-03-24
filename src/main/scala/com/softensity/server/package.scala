package com.softensity

import akka.http.scaladsl.server.Directives

package object server extends Directives {
  type Route = akka.http.scaladsl.server.Route
}
