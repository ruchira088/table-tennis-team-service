package dao

import javax.inject.{Inject, Singleton}

import exceptions.UnableFindTeamException
import models.Team
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TeamDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]
{
  import profile.api._

  private class TeamTable(tag: Tag) extends Table[Team](tag, "TEAMS")
  {
    def id = column[String]("ID", O.PrimaryKey)
    def name = column[String]("NAME", O.Unique)

    def * : ProvenShape[Team] = (id, name) <> (Team.tupled, Team.unapply)
  }

  private val teamTable = TableQuery[TeamTable]

  def all(): Future[List[Team]] = db.run(teamTable.result).map(_.toList)

  def insert(team: Team): Future[Int] = db.run(teamTable += team)

  def findByName(name: String): Future[Team] =
    db.run(teamTable.filter(_.name === name).result.headOption).flatMap {
      case Some(team) => Future successful team
      case None => Future failed UnableFindTeamException(name)
    }

}
