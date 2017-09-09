package dao

import java.sql.Timestamp
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

  private class TeamTable(tag: Tag) extends Table[Team](tag, "teams")
  {
    def id = column[String]("id")
    def name = column[String]("name")
    def createdAt = column[Timestamp]("created_at")
    def pk = primaryKey("pk", (id, createdAt))

    def * : ProvenShape[Team] = (id, name, createdAt) <> ((Team.applyDb _ ).tupled, Team.unapplyDb)
  }

  private val teamTable = TableQuery[TeamTable]

  def all(): Future[List[Team]] = db.run(teamTable.result).map(_.toList)

  def exists(name: String): Future[Boolean] =
    db.run(teamTable.filter(_.name === name).exists.result)

  def insert(team: Team): Future[Int] = db.run(teamTable += team)

  def searchByName(nameKeyword: String): Future[List[Team]] =
    db.run(teamTable.filter(_.name.like(s"%$nameKeyword%")).result).map(_.toList)


  def findByName(name: String): Future[Team] =
    db.run(teamTable.filter(_.name === name).result.headOption).flatMap {
      case Some(team) => Future successful team
      case None => Future failed UnableFindTeamException(name)
    }

}
