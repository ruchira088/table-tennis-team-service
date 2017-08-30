package controllers.utils

import java.util.UUID

object GeneralUtils
{
  def generateRandomUuid(): String = UUID.randomUUID().toString
}
