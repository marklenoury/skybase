package com.marklenoury.skybaseclient

import akka.actor.ActorSystem
import akka.util.Timeout

import scala.concurrent.duration._
import akka.pattern.ask
import com.marklenoury.skybase.common.messages.{GetRequest, SetRequest, DeleteRequest}

class SkybaseClient(remoteAddress: String) {
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("LocalSystem")

  // akka.tcp://Skybase@127.0.0.1:2552
  private val remoteDb = system.actorSelection(s"akka.tcp://Skybase@$remoteAddress/user/skybase-db")

  def set(key: String, value: Object): Unit = {
    this.remoteDb ? SetRequest(key, value)
  }

  def get(key: String) = {
    this.remoteDb ? GetRequest(key)
  }

  def delete(key: String) = {
    this.remoteDb ? DeleteRequest(key)
  }
}
