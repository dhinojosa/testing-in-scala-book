package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FunSpec, Tag, GivenWhenThen, Spec}


class AlbumSpecAll extends FunSpec with ShouldMatchers with GivenWhenThen {
  describe("An Album") {
    it("can add an Artist to the album at construction time", Tag("construction")) {
      given("The album Thriller by Michael Jackson")
      val album = new Album("Thriller", 1981, new Artist("Michael", "Jackson"))

      when("the first act of the album is obtained")
      val act = album.acts.head

      then("the act should be an instance of Artist")
      act.isInstanceOf[Artist] should be(true)

      and("the artist's first name and last name should be Michael Jackson")
      val artist = act.asInstanceOf[Artist]
      artist.firstName === "Michael"
      artist.lastName === "Jackson"
      info("This is still pending, since there may be more to accomplish in this test")
//      pending
    }

    ignore("can add a Producer to an album at construction time") {
      //TODO: Add some logic to add a producer.
    }
  }
}