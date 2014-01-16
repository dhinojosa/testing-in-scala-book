package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import collection.mutable.ListBuffer
import org.scalatest.{FreeSpec, OneInstancePerTest}


class AlbumListOneInstancePerTestFreeSpec extends FreeSpec with ShouldMatchers with OneInstancePerTest {
  val graceJonesDiscography = new ListBuffer[Album]()
  graceJonesDiscography += (new Album("Portfolio", 1977, new Artist("Grace", "Jones")))

  "Given an initial Grace Jones Discography" - {
    "when an additional two albums are added, then the discography size should be 3" in {
      graceJonesDiscography += (new Album("Fame", 1978, new Artist("Grace", "Jones")))
      graceJonesDiscography += (new Album("Muse", 1979, new Artist("Grace", "Jones")))
      graceJonesDiscography.size should be(3)
    }

    "when one additional album is added, then the discography size should be 2" in {
      graceJonesDiscography += (new Album("Warm Leatherette", 1980, new Artist("Grace", "Jones")))
      graceJonesDiscography.size should be(2)
    }
  }

  "Given an initial Grace Jones Discography 2" - {
    "when one additional album is added, then the discography size should be 2" in {
      graceJonesDiscography += (new Album("Nightclubbing", 1981, new Artist("Grace", "Jones")))
      graceJonesDiscography.size should be(2)
    }
  }
}