package com.marklenoury.skybase

import akka.actor.Actor
import akka.event.Logging
import com.marklenoury.skybase.messages.SetRequest

import scala.collection.mutable.HashMap


class SkybaseDb extends Actor {
  val map = HashMap.empty[String, Object]
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) => {
      this.log.info(s"Received SetRequest - key: $key value: $value")
      this.map.put(key, value)
    }

    case _ => {
      this.log.info(s"Received unknown message.")
    }
  }
}
