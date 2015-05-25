package models.chat

import akka.actor._
import akka.pattern.ask

trait ChatRoomFactory { this:Actor =>

  def createChatRoom(name:String) = context.actorOf(Props(classOf[ChatRoom], name), name)

}

