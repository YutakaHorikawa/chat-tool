package controllers

import play.api._
import play.api.mvc._
import services.ChatService

object Application extends Controller with ChatService {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def chat(roomId:String) = WebSocket.async[String] { request =>
    val userName = request.queryString("user_name").headOption.getOrElse("anon")
    chatService.start(roomId, userName)
  }

}

