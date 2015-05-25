package models.chat

import scala.language.postfixOps
import scala.concurrent.duration._
import scala.concurrent.Future

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import play.api._
import play.api.Play.current
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits._

import services._

case class Join(userName:String)
case class Leave(userName:String)
case class NewMember(userName:String)
case class Talk(userName:String, text:String)
case class Joined(enumerator:Enumerator[String])

case class ChatRoom(roomId:String) extends Actor {
  private[this] val (enumerator, channel) = Concurrent.broadcast[String]

  def receive = {
    case Join(userName)       => join(userName)
    case Leave(userName)      => dispatch("system", s"$userName is gone")
    case NewMember(userName)  => dispatch("system", s"$userName joined")
    case Talk(userName, text) => dispatch(userName, text)
  }

  private def join(userName:String) = {
    sender ! Joined(enumerator)
    self ! NewMember(userName)
  }

  private def dispatch(name:String, text:String) = {
    val response = ChatResponse(room = roomId, name = name, text = text)
    channel.push(response.toString)
  }
}

