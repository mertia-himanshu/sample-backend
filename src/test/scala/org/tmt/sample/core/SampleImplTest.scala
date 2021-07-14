package org.tmt.sample.core

import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.tmt.sample.core.models.{UserInfo, GreetResponse}

class SampleImplTest extends AnyWordSpec with Matchers {

  "SampleImpl" must {
    "sayHello should return sample response of 'Hello!!!'" in {
      val sampleImpl = new SampleImpl()
      sampleImpl.greeting(UserInfo("John", "Smith")).futureValue should ===(GreetResponse("Hello user: John Smith!!!"))
    }

    "securedSayHello should return sample response of 'Secured Hello!!!'" in {
      val sampleImpl = new SampleImpl()
      sampleImpl.adminGreeting(UserInfo("John", "Smith")).futureValue should ===(
        Some(GreetResponse("Hello secured user: John Smith!!!"))
      )
    }
  }
}
