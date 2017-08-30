package controllers

import javax.inject.{Inject, Singleton}

import controllers.forms.CreateTeamForm
import controllers.utils.GeneralUtils
import dao.TeamDao
import models.Team
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TeamController @Inject()(teamDao: TeamDao, controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create() = Action.async {
    implicit request: Request[AnyContent] => for {
      CreateTeamForm(name) <- Future.fromTry(CreateTeamForm.fromRequest)
      teamId = GeneralUtils.generateRandomUuid()
      _ <- teamDao.insert(Team(teamId, name))
    } yield Ok
  }

}
