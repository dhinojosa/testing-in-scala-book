package com.oreilly.testingscala

import org.scalacheck.{Gen, Prop, Properties}


object ArtistScalaCheckProperties extends Properties("Testing Artists Thoroughly") {
  property("middleNames") =
    Prop.forAll (Gen.alphaStr, Gen.oneOf(Gen.alphaStr.sample, None), Gen.alphaStr) {
      (firstName: String, middleName: Option[String], lastName: String) =>
        middleName match {
          case Some(x) =>
            val artist = new Artist(firstName, x, lastName)
            artist.fullName == firstName + " " + x + " " + lastName
          case _ =>
            val artist = new Artist(firstName, lastName)
            artist.fullName == firstName + " " + lastName
        }
    }
}
