package services

import scala.concurrent._
import scala.concurrent.duration._
//import scala.concurrent.Future._

import scala.language.postfixOps

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import play.api.libs.iteratee._
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json

import models.chat._
import services._

class ChatRoomServiceImpl {
  private[this] implicit val timeout = Timeout(1 second)

  def join(chatRoom:ActorRef, userName:String) = (chatRoom ? Join(userName)).map {
    case Joined(enumerator) => (in(chatRoom, userName), enumerator)
    case _                  => error(chatRoom)
  }

  private def in(chatRoom:ActorRef, userName:String) = {
    Iteratee.foreach[String](text => chatRoom ! Talk(userName, text)).map(_ => chatRoom ! Leave(userName))
  }

  private def error(chatRoom:ActorRef) = {
    val response = ChatResponse(room = chatRoom.toString, name = "system", text = "can not join")
    (Iteratee.ignore[String], Enumerator[String](response.toString))
  }
}

trait ChatRoomService {
  def chatRoomService = new ChatRoomServiceImpl
}

