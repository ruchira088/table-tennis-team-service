package dao

import javax.inject.{Inject, Singleton}

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

@Singleton()
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile]
{
  import profile.api._

  private class UserTable(tag: Tag) extends Table[User](tag, "USERS")
  {
    def id = column[String]("ID", O.PrimaryKey)
    def teamId = column[String]("TEAM_ID")
    def firstName = column[String]("FIRST_NAME")
    def lastName = column[String]("LAST_NAME")
    def email = column[String]("EMAIL")

    def * : ProvenShape[User] = (id, teamId, firstName, lastName, email) <> (User.tupled, User.unapply)
  }

  private val userTable = TableQuery[UserTable]

  def create(user: User): Future[Int] = db.run(userTable += user)

}
