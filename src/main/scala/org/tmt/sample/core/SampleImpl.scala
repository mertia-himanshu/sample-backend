package org.tmt.sample.core

import org.tmt.sample.core.models.{UserInfo, SampleResponse}

import scala.concurrent.Future

class SampleImpl() {
  def sayHello(userInfo: UserInfo): Future[SampleResponse] = Future.successful(SampleResponse(s"Hello user: ${userInfo.firstname} ${userInfo.lastname}!!!"))

  def securedSayHello(userInfo: UserInfo): Future[Option[SampleResponse]] =
    Future.successful(Some(SampleResponse(s"Hello secured user: ${userInfo.firstname} ${userInfo.lastname}!!!")))
}
