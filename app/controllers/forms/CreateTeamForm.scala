package controllers.forms

import exceptions.FormValidationError
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AnyContent, Request}

import scala.util.{Failure, Success, Try}

case class CreateTeamForm(name: String)

object CreateTeamForm
{
  def fromRequest(implicit request: Request[AnyContent]): Try[CreateTeamForm] =
    Form (
      mapping(
        "name" -> text
      )(CreateTeamForm.apply)(CreateTeamForm.unapply)
    )
      .bindFromRequest()
      .fold(
        form => Failure(FormValidationError(form.errors.toList)),
        Success(_)
      )
}
