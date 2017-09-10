package matchers

trait CustomMatchers
{
  def containsKeyValues(keyValues: Map[String, Any]) =
    JsonContainsKeyValuesMatcher(keyValues.toList)

  def containsKeyValue(keyValue: (String, Any)) =
    JsonContainsKeyValuesMatcher(List(keyValue))
}
