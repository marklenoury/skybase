package com.marklenoury.skybase

import akka.actor.{ActorSystem, Props}

object SkyBase {
  def main(args: Array[String]): Unit = {
    try {
      val system = ActorSystem("Skybase")
      system.actorOf(Props[SkybaseDb], name="skybase-db")
    } catch {
      case ex: Exception => {
        System.err.println(ex.toString)
      }
    }
  }
}
