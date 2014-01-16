package com.oreilly.testingscala

import org.specs2.Specification
import org.specs2.specification.Tags
import org.joda.time.Period

class Specs2TagSpecification extends Specification with Tags {
  def is =
    "The total time of an album is based on the sum of the tracks".title ^
      "When an album is given three tracks" ! testThreeTracks ^ tag("timeLength", "threeTracks") ^
      "When an album is given zero tracks" ! testZeroTracks ^ tag("timeLength", "zeroTracks")

  def testThreeTracks = {
    val beyonceFirstAlbum = new Album("Dangerously in Love", 2003,
      Some(List(
        new Track("Crazy In Love", "3:56"),
        new Track("Naughty Girl", "3:29"),
        new Track("Baby Paul", "4:05")
      )), new Artist("Beyonce", "Knowles"))
    beyonceFirstAlbum.period must be_== (new Period(0, 10, 90, 0))
  }

  def testZeroTracks = {
    val frankZappaAlbum = new Album("We're only in it for the Money", 1968, None, new Band("The Mothers of Invention"))
    frankZappaAlbum.period must be_== (Period.ZERO)
  }
}
