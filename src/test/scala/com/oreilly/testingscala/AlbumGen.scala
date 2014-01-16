package com.oreilly.testingscala

import org.scalacheck.{Gen, Arbitrary}

object AlbumGen {
  implicit val album: Arbitrary[Album] = Arbitrary {
    for {
      a <- Gen.alphaStr
      b <- Gen.choose(1900, 2012)
      c <- Gen.alphaStr
    } yield (new Album(a, b, new Band(c)))
  }
}