package controllers

import javax.inject.{Inject, Singleton}

import controllers.forms.CreateTeamForm
import dao.TeamDao
import exceptions.TeamAlreadyExistsException
import models.Team
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import utils.{GeneralUtils, ScalaUtils}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TeamController @Inject()(teamDao: TeamDao, controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create() = Action.async {
    implicit request: Request[AnyContent] => for {
      CreateTeamForm(name) <- Future.fromTry(CreateTeamForm.fromRequest)
      exists <- teamDao.exists(name)
      _ <- ScalaUtils.predicate(!exists, TeamAlreadyExistsException(name))
     teamId = GeneralUtils.generateRandomUuid()
     _ <- teamDao.insert(Team(teamId, name, DateTime.now()))
    } yield Ok(Json.obj("Result" -> "Success"))
  }

}
