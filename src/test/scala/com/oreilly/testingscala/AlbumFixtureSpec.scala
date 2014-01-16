package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec

class AlbumFixtureSpec extends FunSpec with ShouldMatchers {

  def fixture = new {
    val letterFromHome = new Album("Letter from Home", 1989, new Band("Pat Methany Group"))
  }

  describe("The Letter From Home Album by Pat Metheny") {
    it("should get the year 1989 from the album") {
      val album = fixture.letterFromHome
      album.year should be(1989)
    }
  }
}