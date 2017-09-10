package utils

import org.apache.commons.lang3.StringUtils
import org.scalatest.matchers.MatchResult

object MatcherUtils
{
  def failedMatchResult(failureMessage: String): MatchResult =
    MatchResult(false, failureMessage, StringUtils.EMPTY)

  def failedMatchResult(throwable: Throwable): MatchResult =
    failedMatchResult(throwable.getMessage)

  def successfulMatchResult(successMessage: String): MatchResult =
    MatchResult(true, StringUtils.EMPTY, successMessage)
}
