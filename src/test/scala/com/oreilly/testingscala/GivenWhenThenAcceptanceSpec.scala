package com.oreilly.testingscala

import org.specs2.Specification
import org.specs2.specification.{Then, When, Given}


class GivenWhenThenAcceptanceSpec extends Specification {
  def is = {
      "Demonstrating a Given When Then block for a Specs2 Specification".title ^
        "Given the first name ${David} and the last ${Bowie} create an Artist" ^ setUpBowie ^
        "When we add the artist to an album called ${Hunky Dory} with the year ${1971}" ^ setUpHunkyDory ^
        "And when an the album is added to a jukebox" ^ addTheAlbumToAJukebox ^
        "Then the jukebox should have one album whose name is ${Hunky Dory}" ^ result
  }

  object setUpBowie extends Given[Artist] {
    def extract(text: String) = {
      val tokens = extract2(text)
      new Artist(tokens._1, tokens._2)
    }
  }

  object setUpHunkyDory extends When[Artist, Album] {
    def extract(p: Artist, text: String) = {
      val tokens = extract2(text)
      new Album(tokens._1, tokens._2.toInt, p)
    }
  }

  object addTheAlbumToAJukebox extends When[Album, JukeBox] {
    def extract(p: Album, text: String) = new JukeBox(Some(List(p)))
  }

  object result extends Then[JukeBox] {
    def extract(t: JukeBox, text: String) = t.albums.get must have size (1)
  }

}