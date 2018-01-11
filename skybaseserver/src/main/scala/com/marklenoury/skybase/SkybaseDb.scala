package com.marklenoury.skybase

import akka.actor.{Actor, Status}
import akka.event.Logging
import com.marklenoury.skybase.common.messages.{GetRequest, KeyNotFoundException, SetRequest}

import scala.collection.mutable.HashMap


class SkybaseDb extends Actor {
  val map = HashMap.empty[String, Object]
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) => {
      this.log.info(s"Received SetRequest - key: $key value: $value")
      this.map.put(key, value)
      sender() ! Status.Success
    }

    case GetRequest(key) => {
      this.log.info(s"Recieved GetRequest - key: $key")
      val result: Option[Object] = this.map.get(key)
      result match {
        case Some(x) => {
          sender() ! x
        }

        case None => {
          sender() ! Status.Failure(new KeyNotFoundException(key))
        }

        case _ => {
          sender() ! Status.Failure(new ClassNotFoundException())
        }
      }
    }

    case _ => {
      this.log.info(s"Received unknown message.")
    }
  }
}
