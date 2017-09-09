package dao

import java.sql.Timestamp
import javax.inject.{Inject, Singleton}

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]
{
  import profile.api._

  private class UserTable(tag: Tag) extends Table[User](tag, "users")
  {
    def id = column[String]("id")
    def teamId = column[String]("team_id")
    def username = column[String]("username")
    def createAt = column[Timestamp]("created_at")
    def firstName = column[Option[String]]("first_name")
    def lastName = column[Option[String]]("last_name")
    def email = column[String]("email")
    def pk = primaryKey("pk", (id, createAt))

    def * : ProvenShape[User] = (id, teamId, username, createAt, firstName, lastName, email) <>
      ((User.applyDb _).tupled, User.unapplyDb)
  }

  private val userTable = TableQuery[UserTable]

  def create(user: User): Future[Int] = db.run(userTable += user)

  def findByTeamId(teamId: String): Future[List[User]] =
    db.run(userTable.filter(_.teamId === teamId).result).map(_.toList)

  def exists(username: String): Future[Boolean] =
    db.run(userTable.filter(_.username === username).exists.result)
}
