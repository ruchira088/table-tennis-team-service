package controllers

import javax.inject.{Inject, Singleton}

import controllers.forms.CreateUserForm
import dao.{TeamDao, UserDao}
import models.User
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc._
import utils.GeneralUtils

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(userDao: UserDao, teamDao: TeamDao, controllerComponents: ControllerComponents)
                              (implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] => for {
      CreateUserForm(teamName, username, firstName, lastName, email) <- Future.fromTry(CreateUserForm.fromRequest)
      team <-teamDao.findByName(teamName)
      userId = GeneralUtils.generateRandomUuid()
      user = User(userId, team.id, username, DateTime.now(), firstName, lastName, email)
      _ <- userDao.create(user)
    } yield Ok(Json.toJson(user))
  }
}
