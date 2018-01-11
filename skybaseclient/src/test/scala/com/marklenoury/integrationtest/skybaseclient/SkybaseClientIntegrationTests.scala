package com.marklenoury.integrationtest.skybaseclient

import com.marklenoury.skybaseclient.SkybaseClient
import org.scalatest.FunSuite
import scala.concurrent.duration._
import scala.concurrent.Await

class SkybaseClientIntegrationTests extends FunSuite {
  test("SkybaseClientShouldSetValue") {
    // arrange
    val client = new SkybaseClient("127.0.0.1:2552")

    // act
    client.set("test", new Integer(123))
    val futureResult = client.get("test")
    val result = Await.result(futureResult, 10 seconds)

    // assert
    assert(result == 123)
  }
}
