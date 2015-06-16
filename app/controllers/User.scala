package controllers

import play.api._
import play.api.mvc._

object User extends Controller {

  def signup = Action {
    Ok(views.html.signup("signup"))
  }

  def create = Action { implicit request =>
    Ok(views.html.signup("signup"))
  }



}

