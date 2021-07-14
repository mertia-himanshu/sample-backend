package org.tmt.sample.http

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.BasicDirectives
import akka.http.scaladsl.testkit.ScalatestRouteTest
import csw.aas.http.AuthorizationPolicy.RealmRolePolicy
import csw.aas.http.SecurityDirectives
import csw.location.api.models.ComponentType.Service
import csw.location.api.models.Connection.HttpConnection
import csw.location.api.models._
import csw.prefix.models.Prefix
import io.bullet.borer.compat.AkkaHttpCompat
import msocket.security.models.AccessToken
import org.mockito.MockitoSugar.{mock, reset, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec
import org.tmt.sample.TestHelper
import org.tmt.sample.core.SampleImpl
import org.tmt.sample.core.models.{UserInfo, SampleResponse}

import scala.concurrent.Future
import scala.util.Random

class SampleRouteTest extends AnyWordSpec with ScalatestRouteTest with AkkaHttpCompat with BeforeAndAfterEach with HttpCodecs {

  private val service1: SampleImpl                   = mock[SampleImpl]
  private val service2                               = mock[JSampleImplWrapper]
  private val securityDirectives: SecurityDirectives = mock[SecurityDirectives]
  private val token: AccessToken                     = mock[AccessToken]
  private val accessTokenDirective                   = BasicDirectives.extract(_ => token)

  private val route: Route = new SampleRoute(service1, service2, securityDirectives).route

  override protected def beforeEach(): Unit = reset(service1, securityDirectives)

  "SampleRoute" must {
    "sayHello must delegate to service1.sayHello" in {
      val response = SampleResponse(Random.nextString(10))
      val john     = UserInfo("John", "Smith")
      when(service1.sayHello(john)).thenReturn(Future.successful(response))

      Post("/sayHello", john) ~> route ~> check {
        verify(service1).sayHello(UserInfo("John", "Smith"))
        responseAs[SampleResponse] should ===(response)
      }
    }

    "sayBye must delegate to service2.sayBye" in {
      val response = SampleResponse(Random.nextString(10))
      when(service2.sayBye()).thenReturn(Future.successful(response))

      Get("/sayBye") ~> route ~> check {
        verify(service2).sayBye()
        responseAs[SampleResponse] should ===(response)
      }
    }

    "securedSayHello must check for Esw-user role and delegate to service1.securedSayHello" in {
      val response = SampleResponse(Random.nextString(10))
      val policy   = RealmRolePolicy("Esw-user")
      val john     = UserInfo("John", "Smith")
      when(securityDirectives.sPost(policy)).thenReturn(accessTokenDirective)
      when(service1.securedSayHello(john)).thenReturn(Future.successful(Some(response)))

      Post("/securedSayHello", john) ~> route ~> check {
        verify(service1).securedSayHello(UserInfo("John", "Smith"))
        verify(securityDirectives).sPost(policy)
        responseAs[Option[SampleResponse]] should ===(Some(response))
      }
    }
  }

  val connection: Connection.HttpConnection = HttpConnection(ComponentId(Prefix(TestHelper.randomSubsystem, "sample"), Service))
}
