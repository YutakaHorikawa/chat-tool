package services

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.Future._

import scala.language.postfixOps

import akka.actor._
import akka.util.Timeout

import play.api.libs.iteratee._
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
//import play.api.libs.json.Json

import models.chat._
import services._

class ChatServiceImpl extends ChatRoomRepository with ChatRoomService {
  // 
  def start(roomId:String, userName:String) = repository.chatRoom(roomId).flatMap {
    case chatRoom:ActorRef => chatRoomService.join(chatRoom, userName)
    case _                 => error
  }

  private def error = future {
    val response = ChatResponse(room = "none", name = "system", text = "chat room not found")
    (Iteratee.ignore[String], Enumerator[String](response.toString))
  }
}

trait ChatService {
  def chatService = new ChatServiceImpl
}

