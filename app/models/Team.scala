package models

import java.sql.Timestamp

import org.joda.time.DateTime

case class Team(id: String, name: String, createAt: DateTime)

object Team
{
  def applyDb(id: String, name: String, createAt: Timestamp): Team =
    Team(id, name, new DateTime(createAt.getTime))

  def unapplyDb(team: Team): Option[(String, String, Timestamp)] =
    Some(team.id, team.name, new Timestamp(team.createAt.getMillis))
}
