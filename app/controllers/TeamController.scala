package controllers

import javax.inject.{Inject, Singleton}

import controllers.forms.CreateTeamForm
import dao.{TeamDao, UserDao}
import exceptions.TeamAlreadyExistsException
import models.Team
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc._
import utils.{GeneralUtils, ScalaUtils}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TeamController @Inject()(teamDao: TeamDao, userDao: UserDao, controllerComponents: ControllerComponents)
                              (implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      for {
        CreateTeamForm(name) <- Future.fromTry(CreateTeamForm.fromRequest)
        exists <- teamDao.exists(name)
        _ <- ScalaUtils.predicate(!exists, TeamAlreadyExistsException(name))
        teamId = GeneralUtils.generateRandomUuid()
        team = Team(teamId, name, DateTime.now())
        _ <- teamDao.insert(team)
      } yield Ok(Json.toJson(team))
  }

  def search(nameKeyword: String): Action[AnyContent] = Action async {
    for {
      teams <- teamDao.searchByName(nameKeyword)
    } yield Ok(Json.toJson(teams))
  }

  def get(name: String): Action[AnyContent] = Action.async {
    for {
      team <- teamDao.findByName(name)
      users <- userDao.findByTeamId(team.id)
    } yield Ok(Json.obj(
      "team" -> Json.toJson(team),
      "users" -> Json.toJson(users)
    ))
  }

}
