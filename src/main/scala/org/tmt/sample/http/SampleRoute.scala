package org.tmt.sample.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import csw.aas.http.AuthorizationPolicy.RealmRolePolicy
import csw.aas.http.SecurityDirectives
import org.tmt.sample.core.SampleImpl
import org.tmt.sample.core.models.UserInfo

import scala.concurrent.ExecutionContext

class SampleRoute(service1: SampleImpl, service2: JSampleImplWrapper, securityDirectives: SecurityDirectives) (implicit  ec: ExecutionContext) extends HttpCodecs {

  val route: Route = post { path("sayHello") {
    entity(as[UserInfo]) { userInfo =>
      complete(service1.sayHello(userInfo))
        }
    } ~
    path("securedSayHello") {
      securityDirectives.sPost(RealmRolePolicy("Esw-user")) { token =>
        entity(as[UserInfo]) { userInfo => complete(service1.securedSayHello(userInfo)) }
      }
    }
  } ~
    path("sayBye") {
      complete(service2.sayBye())
    }
}
