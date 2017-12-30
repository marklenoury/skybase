package com.marklenoury.skybase.test

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.marklenoury.skybase.SkybaseDb
import com.marklenoury.skybase.messages.SetRequest
import org.scalatest.FunSuite

class SkybaseDbTests extends FunSuite {
  test("SetRequest") {
    // arrange
    implicit val system = ActorSystem()
    val actorRef = TestActorRef(new SkybaseDb)

    // act
    actorRef ! SetRequest("someKey", "someValue")

    // assert
    assert(actorRef.underlyingActor.map.keySet.contains("someKey"))
    assert(actorRef.underlyingActor.map("someKey") == "someValue")
  }
}
