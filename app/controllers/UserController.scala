package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

@Singleton
class UserController @Inject()(controllerComponents: ControllerComponents)
  extends AbstractController(controllerComponents)
{
  def create() = Action.async {
    implicit request: Request[AnyContent] => ???
  }
}
