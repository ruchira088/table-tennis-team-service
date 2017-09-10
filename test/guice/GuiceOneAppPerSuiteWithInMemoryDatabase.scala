package guice

import matchers.CustomMatchers
import org.scalatest.TestSuite
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Mode
import play.api.inject.guice.GuiceApplicationBuilder
import utils.TestConfigUtils

trait GuiceOneAppPerSuiteWithInMemoryDatabase extends GuiceOneAppPerSuite with CustomMatchers
{
  self: TestSuite =>

  override def fakeApplication() =
    GuiceApplicationBuilder().configure(TestConfigUtils.inMemoryDatabaseConfiguration).in(Mode.Test).build()
}
