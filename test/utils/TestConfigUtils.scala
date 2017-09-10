package utils

import org.apache.commons.lang3.StringUtils

object TestConfigUtils
{
  def inMemoryDatabaseConfiguration: Map[String, String] =
    Map(
      "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
      "slick.dbs.default.db.driver" -> "org.h2.Driver",
      "slick.dbs.default.db.url" -> "jdbc:h2:mem:team_service;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=FALSE",
      "slick.dbs.default.db.dataSourceClass" -> StringUtils.EMPTY
    )
}
