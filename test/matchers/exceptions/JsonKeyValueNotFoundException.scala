package matchers.exceptions

case class JsonKeyValueNotFoundException[A](key: String, value: A) extends Exception