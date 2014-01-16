package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec

class CompilationAlbumSpec extends FunSpec with ShouldMatchers {
  describe("CompilationAlbums extends Albums") {
    it("should contain the artists of all pairings") {
      val pureMoods = new CompilationAlbum("Pure Moods", 1999,
        (new Track("Theme from Henry\'s Game", "2:28"), List(new Band("Clannad"))),
        (new Track("Return to Innocence", "2:28"), List(new Band("Enigma"))),
        (new Track("Sacred Spirit", "5:10"), List(new Band("Yeha Noha"))))
      pureMoods.acts.size should be(3)
    }

    it("should contain the artists of all pairings, but distinct") {
      val enigma = new Band("Enigma")
      val pureMoods = new CompilationAlbum("Pure Moods", 1999,
        (new Track("Theme from Henry\'s Game", "2:28"), List(new Band("Clannad"))),
        (new Track("Return to Innocence", "2:28"), List(enigma)),
        (new Track("Knocking On Forbidden Doors", "3:54"), List(enigma)))
      pureMoods.acts.size should be(2)
    }
  }


}
