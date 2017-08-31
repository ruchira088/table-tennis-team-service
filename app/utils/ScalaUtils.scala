package utils

import scala.concurrent.Future

object ScalaUtils
{
  def predicate(condition: Boolean, exception: => Exception): Future[Unit] =
    if (condition) Future.successful(()) else Future.failed(exception)
}
