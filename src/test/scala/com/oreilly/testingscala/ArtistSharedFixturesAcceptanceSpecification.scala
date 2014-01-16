package com.oreilly.testingscala

import org.specs2.Specification
import org.specs2.execute.Result

/**
 *
 *
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 12/30/11
 * Time: 9:25 PM
 *
 */

class ArtistSharedFixturesAcceptanceSpecification extends Specification { def is =
  "Using the same shared instance of Barry White add some albums"                    ^ br ^
  "Then send some questions about the test"                                          ^
  "Add two albums, and request the count" ! add2andCount(_.albums must be size(2))   ^
  "Add three albums, and request the count" ! add3andCount(_.albums must be size(3)) ^ end


  val masterOfLove = new Artist("Barry", "White")

  def add2andCount(f:(Artist => Result)) = {
    val masterOfLoveWithAddedAlbums = masterOfLove
      .addAlbum(new Album("I've got so much to give", 1973, masterOfLove))
      .addAlbum(new Album("Stone 'Gon", 1973, masterOfLove))
    f(masterOfLoveWithAddedAlbums)
  }

   def add3andCount(f:(Artist => Result)) = {
    val masterOfLoveWithAddedAlbums = masterOfLove
      .addAlbum(new Album("I've got so much to give", 1973, masterOfLove))
      .addAlbum(new Album("Stone 'Gon", 1973, masterOfLove))
      .addAlbum(new Album("Can't get enough", 1974, masterOfLove))
    f(masterOfLoveWithAddedAlbums)
  }


}