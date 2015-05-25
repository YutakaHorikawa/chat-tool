package models.chat

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import akka.actor.Identify._

import scala.concurrent.duration._
import scala.util.Success
import scala.language.postfixOps

import play.api._
import play.api.Play.current
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

case class FindChatRoom(roomId:String)

class ChatRoomRepositoryActor extends Actor with ChatRoomFactory {
  implicit val timeout = Timeout(1 second)

  def receive = {
    case FindChatRoom(roomId) => sender ! chatRoom(roomId)
    case x                    => println(x.toString)
  }

  private def chatRoom(roomId:String) = context.child(roomId) match {
    case Some(room) => room
    case None => createChatRoom(roomId)
  }
}

object ChatRoomRepository {
  implicit val timeout = Timeout(1 second)
  private[this] val repository = Akka.system.actorOf(Props[ChatRoomRepositoryActor])

  def chatRoom(roomId:String) = repository ? FindChatRoom(roomId)
}

trait ChatRoomRepository {
  val repository = ChatRoomRepository
}

