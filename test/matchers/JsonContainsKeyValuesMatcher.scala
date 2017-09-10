package matchers

import matchers.exceptions.JsonKeyValueNotFoundException
import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.libs.json.{JsObject, JsResultException, JsValue, Reads}
import utils.MatcherUtils._

import scala.util.{Failure, Success, Try}

case class JsonContainsKeyValuesMatcher(expectedKeyValues: List[(String, Any)]) extends Matcher[JsValue]
{
  override def apply(left: JsValue): MatchResult = left match {
    case jsonObject @ JsObject(_) =>
      JsonContainsKeyValuesMatcher.jsonObjectContainsKeyValues(jsonObject, expectedKeyValues)
    case _ => failedMatchResult(s"$left is NOT of type JsObject")
  }
}

object JsonContainsKeyValuesMatcher
{
  private def compare[A](value_1: JsValue, value_2: A)(implicit reads: Reads[A]): Boolean =
    try {
      value_1.as[A] == value_2
    } catch {
      case JsResultException(_) => false
    }

  def isKeyValueMatch(expectedKey: String, expectedValue: Any)(actual: (String, JsValue)): Boolean =
  {
    val (actualKey, actualValue) = actual

    if (expectedKey != actualKey)
      false
    else
      expectedValue match {
        case stringValue: String => compare(actualValue, stringValue)
        case intValue: Int => compare(actualValue, intValue)
        case booleanValue: Boolean => compare(actualValue, booleanValue)
        case _ => false
      }
  }

  def jsonObjectContainsKeyValues(jsonObject: JsObject, expectedKeyValues: List[(String, Any)]): MatchResult =
    containsKeyValues(jsonObject, expectedKeyValues) match {
      case Success(_) => successfulMatchResult("Successful Match !!!")
      case Failure(exception) => failedMatchResult(exception)
    }

  def containsKeyValues(jsonObject: JsObject, expectedKeyValues: List[(String, Any)]): Try[Unit] =
    expectedKeyValues match {
      case Nil => Success(())
      case (key, value) :: tail =>
        if (jsonObject.fields.toList.exists(isKeyValueMatch(key, value)))
          containsKeyValues(jsonObject, tail)
        else
          Failure(JsonKeyValueNotFoundException(key, value))
    }
}