package guice

import org.scalatest.TestSuite
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Mode
import play.api.inject.guice.GuiceApplicationBuilder
import utils.TestConfigUtils

trait GuiceOneAppPerSuiteWithInMemoryDatabase extends GuiceOneAppPerSuite {
  self: TestSuite =>

  override def fakeApplication() =
    GuiceApplicationBuilder().configure(TestConfigUtils.inMemoryDatabaseConfiguration).in(Mode.Test).build()
}
