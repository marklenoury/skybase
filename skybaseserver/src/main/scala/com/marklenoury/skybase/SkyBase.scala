package com.marklenoury.skybase

import scopt.OptionParser
import akka.actor.{ActorSystem, Props}

case class SkybaseOptionsConfig(
                               uuid: Option[String] = None,
                               parents: Map[String, String] = Map()
                               )

object SkyBase {
  def buildParser() = {
    val parser = new scopt.OptionParser[SkybaseOptionsConfig]("SkyBase") {
      opt[String]('u', "uuid")
        .required()
        .action((x, c) =>
          c.copy(uuid = Some(x))
        )

      opt[Map[String, String]]("parents")
        .required()
    }
  }

  def main(args: Array[String]): Unit = {
    try {
      val system = ActorSystem("Skybase")
      system.actorOf(Props(new SkybaseDb("")), name="skybase-db")
    } catch {
      case ex: Exception => {
        System.err.println(ex.toString)
      }
    }
  }
}
