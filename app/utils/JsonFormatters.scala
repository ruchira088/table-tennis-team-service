package utils

import org.joda.time.DateTime
import play.api.libs.json._

import scala.util.control.NonFatal

object JsonFormatters
{
  implicit val dateTimeFormat: Format[DateTime] = new Format[DateTime]
  {
    override def writes(dateTime: DateTime): JsValue = JsString(dateTime.toString)

    override def reads(json: JsValue): JsResult[DateTime] = try {
      JsSuccess(DateTime.parse(json.as[String]))
    } catch {
      case NonFatal(_) => JsError()
    }
  }

}
