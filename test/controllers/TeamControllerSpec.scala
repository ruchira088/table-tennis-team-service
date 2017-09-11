package controllers

import guice.GuiceOneAppPerSuiteWithInMemoryDatabase
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}

class TeamControllerSpec extends PlaySpec with GuiceOneAppPerSuiteWithInMemoryDatabase with Injecting
{

  "TeamController create" should {

    "create a user from the router" in {
      val request = FakeRequest(POST, "/team")
        .withJsonBody(
          Json.obj("name" -> "Juno")
        )

      val response = route(app, request).get

      status(response) mustBe OK
      contentAsJson(response) must containsKeyValue("name" -> "Juno")
    }
  }
}
