package org.tmt.sample.core

import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.tmt.sample.core.models.{UserInfo, SampleResponse}

class SampleImplTest extends AnyWordSpec with Matchers {

  "SampleImpl" must {
    "sayHello should return sample response of 'Hello!!!'" in {
      val sampleImpl = new SampleImpl()
      sampleImpl.sayHello(UserInfo("John", "Smith")).futureValue should ===(SampleResponse("Hello user: John Smith!!!"))
    }

    "securedSayHello should return sample response of 'Secured Hello!!!'" in {
      val sampleImpl = new SampleImpl()
      sampleImpl.securedSayHello(UserInfo("John", "Smith")).futureValue should ===(Some(SampleResponse("Hello secured user: John Smith!!!")))
    }
  }
}
