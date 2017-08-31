package models

import java.sql.Timestamp

import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}

case class User(
     id: String,
     teamId: String,
     username: String,
     createdAt: DateTime,
     firstName: Option[String],
     lastName: Option[String],
     email: String
)

object User
{
  import utils.JsonFormatters._

  implicit val jsonFormat: OFormat[User] = Json.format[User]

  def applyDb(
     id: String,
     teamId: String,
     username: String,
     createdAt: Timestamp,
     firstName: Option[String],
     lastName: Option[String],
     email: String
   ) = User(id, teamId, username, new DateTime(createdAt.getTime), firstName, lastName, email)

  def unapplyDb(user: User): Option[(String, String, String, Timestamp, Option[String], Option[String], String)] =
    Some(
      user.id,
      user.teamId,
      user.username,
      new Timestamp(user.createdAt.getMillis),
      user.firstName,
      user.lastName,
      user.email
    )
}