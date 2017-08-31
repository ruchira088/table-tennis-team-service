package controllers.forms

import exceptions.FormValidationError
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AnyContent, Request}

import scala.util.{Failure, Success, Try}

case class CreateUserForm(teamName: String, username: String, firstName: Option[String], lastName: Option[String], email: String)

object CreateUserForm
{
  def fromRequest(implicit request: Request[AnyContent]): Try[CreateUserForm] =
    Form(
      mapping(
        "teamName" -> text,
        "username" -> text,
        "firstName" -> optional(text),
        "lastName" -> optional(text),
        "email" -> email
      )(CreateUserForm.apply)(CreateUserForm.unapply)
    )
    .bindFromRequest()
    .fold(
      form => Failure(FormValidationError(form.errors.toList)),
      Success(_)
    )
}