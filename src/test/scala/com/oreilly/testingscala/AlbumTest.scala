package com.oreilly.testingscala

import org.scalatest.FunSpec
import org.scalatest.matchers.{MustMatchers, ShouldMatchers}

class AlbumTest extends FunSpec with MustMatchers {
  describe("An Album") {
    it("can add an Artist object to the album") {
      val album = new Album("Thriller", 1981,
        new Artist("Michael", "Jackson"))
      album.title must be("Thriller")
    }

    it("can return the artist from the album") {

    }
  }

  describe("An Album from the 1990s") {

  }
}
