package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{GivenWhenThen, FeatureSpec}

class AlbumFeatureSpec extends FeatureSpec with ShouldMatchers with GivenWhenThen {

  feature("An album's default constructor should support a parameter that accepts Option(List(Tracks))") {
    scenario("Album's default constructor is given a list of the 3 tracks exactly for the tracks parameter") {
      val depecheModeCirca1990 = new Band("Depeche Mode",
        new Artist("Dave", "Gahan"),
        new Artist("Martin", "Gore"),
        new Artist("Andrew", "Fletcher"),
        new Artist("Alan", "Wilder"))

      val album = new Album("Black Celebration", 1990,
        Some(List(new Track("Black Celebration"),
          new Track("Fly on the Windscreen"),
          new Track("A Question of Lust"))), List(depecheModeCirca1990): _*)

      album.tracks.get should have size (3)
    }

    scenario("Album's default constructor is given a None for the tracks parameter") {
      given("the band, the Doobie Brothers from 1973")
      val theDoobieBrothersCirca1973 = new Band("The Doobie Brothers", new Artist("Tom", "Johnston"),
        new Artist("Patrick", "Simmons"),
        new Artist("Tiran", "Porter"),
        new Artist("Keith", "Knudsen"),
        new Artist("John", "Hartman"))


      when("the album is instantiated with the title, the year, none tracks, and the Doobie Brothers")
      val album = new Album("The Captain and Me", 1973, None, theDoobieBrothersCirca1973)

      then("calling the albums's title, year, tracks, acts property should yield the same results")
      album.title should be("The Captain and Me")
      album.year should be(1973)
      album.tracks should be(None)
      album.acts(0) should be(theDoobieBrothersCirca1973)
    }

    scenario("Album's default constructor is given null for the tracks parameter") (pending)
  }


  feature("An album should have an addTrack method that takes a track and returns an immutable copy of the Album with the added track") {}
}
