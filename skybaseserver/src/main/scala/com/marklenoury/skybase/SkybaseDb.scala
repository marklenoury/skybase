package com.marklenoury.skybase

import akka.actor.{Actor, ActorRef, ActorSystem, ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider, Status}
import akka.event.Logging
import com.marklenoury.skybase.common.messages.{DeleteRequest, GetRequest, KeyNotFoundException, SetRequest}

import scala.collection.mutable.HashMap

class RemoteAddressExtensionImpl(system: ExtendedActorSystem) extends Extension {
  def address = system.provider.getDefaultAddress
}

object RemoteAddressExtension extends ExtensionId[RemoteAddressExtensionImpl]
with ExtensionIdProvider {
  override def lookup = RemoteAddressExtension
  override def createExtension(system: ExtendedActorSystem) = new RemoteAddressExtensionImpl(system)
  override def get(system: ActorSystem): RemoteAddressExtensionImpl = super.get(system)
}



class SkybaseDb(uuid: String) extends Actor {
  val map = HashMap.empty[String, Object]
  val log = Logging(context.system, this)
  var replicationNetworkSize = 3
  var equivalenceClass = this.calculateEquivalenceClass
  val peerNodeList = HashMap.empty[String, ActorRef]

  //val remoteAddressExtensionImpl = new RemoteAddressExtensionImpl(context.system)
  val remoteAddr = RemoteAddressExtension(context.system).address
  val tester = self.path.toStringWithAddress(remoteAddr)
  this.log.info(s"TEST: ${tester}")

  def calculateEquivalenceClass: Int = {
    this.uuid.hashCode % this.replicationNetworkSize
  }

  override def receive: Receive = {
    case DeleteRequest(key) => {
      this.log.info(s"Received DeleteRequest - key: $key")
      if (this.map.contains(key)) {
        this.map.remove(key)
      }
      sender() ! Status.Success
    }

    case SetRequest(key, value) => {
      this.log.info(s"Received SetRequest - key: $key value: $value")
      this.map.put(key, value)
      sender() ! Status.Success
    }

    case GetRequest(key) => {
      this.log.info(s"Received GetRequest - key: $key")
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
