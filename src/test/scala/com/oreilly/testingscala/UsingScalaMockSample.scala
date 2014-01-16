package com.oreilly.testingscala

import org.scalatest.{FunSpec, Spec}
import org.scalatest.matchers.MustMatchers
import org.scalamock.scalatest.MockFactory
import org.scalamock.generated.GeneratedMockFactory


class UsingScalaMockSample extends FunSpec with MustMatchers with MockFactory with GeneratedMockFactory {
  describe("ScalaMocks can create mocks of various structures") {
    it("can create a mock for a trait") {
      val daoMock = mock[DAO]

      //set up actual values to be used.
      val theGratefulDead: Band = new Band("Grateful Dead")
      val wyntonMarsalis: Artist = new Artist("Wynton", "Marsalis")
      val psychedelicFurs: Band = new Band("Psychedelic Furs")
      val ericClapton: Artist = new Artist("Eric", "Clapton")

      val workingmansDead = new Album("Workingman's Dead", 1970, theGratefulDead)
      val midnightToMidnight = new Album("Midnight to Midnight", 1987, psychedelicFurs)
      val wyntonAndClapton = new Album("Wynton Marsalis and Eric Clapton play the Blues", 2011, wyntonMarsalis, ericClapton)

      val jukeBox = new JukeBox(Some(List(workingmansDead, midnightToMidnight, wyntonAndClapton)))

      //create the subject under test
      val jukeboxStorageService = new JukeboxStorageService(daoMock)

      daoMock.expects.persist(workingmansDead)
      daoMock.expects.persist(midnightToMidnight)
      daoMock.expects.persist(wyntonAndClapton)

      daoMock.expects.persist(theGratefulDead)
      daoMock.expects.persist(psychedelicFurs)
      daoMock.expects.persist(wyntonMarsalis)
      daoMock.expects.persist(ericClapton)

      jukeboxStorageService.persist(jukeBox)
    }

    it("can also mock regular object, and along with other traits") {
      val daoMock = mock[DAO]
      //set up actual values to be used.
      val theGratefulDead: Band = mock[Band]
      val wyntonMarsalis: Artist = mock[Artist]
      val psychedelicFurs: Band = mock[Band]
      val ericClapton: Artist = mock[Artist]

      val workingmansDead = mock[Album]
      val midnightToMidnight = mock[Album]
      val wyntonAndClapton = mock[Album]

      val jukeBox = mock[JukeBox]

      //create the subject under test
      val jukeboxStorageService = new JukeboxStorageService(daoMock)

      inSequence {

        jukeBox.expects.albums returning (Some(List(workingmansDead, midnightToMidnight, wyntonAndClapton)))

        daoMock.expects.persist(workingmansDead)
        workingmansDead.expects.acts returning (List(theGratefulDead))
        daoMock.expects.persist(theGratefulDead)

        daoMock.expects.persist(midnightToMidnight)
        midnightToMidnight.expects.acts returning (List(psychedelicFurs))
        daoMock.expects.persist(psychedelicFurs)

        daoMock.expects.persist(wyntonAndClapton)
        wyntonAndClapton.expects.acts returning (List(ericClapton, wyntonMarsalis))
        daoMock.expects.persist(ericClapton)
        daoMock.expects.persist(wyntonMarsalis)
      }

      jukeboxStorageService.persist(jukeBox)
    }

    it("can mock a singleton object") {
      val bruceSpringsteenFactory = mockObject(BruceSpringsteenFactory)

      val albumMock1 = mock[Album]
      val albumMock2 = mock[Album]
      val albumMock3 = mock[Album]

      albumMock1.expects.year returning (1978)
      albumMock2.expects.year returning (1990)
      albumMock3.expects.year returning (1999)

      bruceSpringsteenFactory.expects.discography returning List(albumMock1, albumMock2, albumMock3)

      BruceSpringsteenStatistics.numberOfAlbums must be(3)
      BruceSpringsteenStatistics.averageYear must be((1978 + 1990 + 1999) / 3)
    }


    it("can mock a companion object") {
      val daoMockCompanion = mockObject(DAO)

      val daoMockMySql = mock[DAO]
      val daoMockDB2 = mock[DAO]


      //Important to play with the same reference.

      val peterMurphy: Artist = new Artist("Peter", "Murphy")

      val album = new Album("Cascade", 1995, peterMurphy)

      daoMockCompanion.expects.createMySqlDAO returning (daoMockMySql)
      daoMockCompanion.expects.createDB2DAO returning (daoMockDB2)

      inSequence {
        daoMockMySql.expects.persist(album)
        daoMockDB2.expects.persist(album)
        daoMockMySql.expects.persist(peterMurphy)
        daoMockDB2.expects.persist(peterMurphy)
      }

      val albumMultipleStorageService = new AlbumMultipleStorageService()
      albumMultipleStorageService.persist(album);
    }

    it("can mock a function") {
      val styxAlbum: Album = new Album("Styx Album", 1945, new Band("Styx"))
      val sarahMcLaughlinAlbum: Album = new Album("Sarah McLaughlin Album", 1997, new Artist("Sarah", "McLaughlin"))
      val billyJoelAlbum: Album = new Album("Billy Joel Album", 1977, new Artist("Billy", "Joel"))

      val albumFunction = mockFunction[Album, Int]
      albumFunction expects (styxAlbum) returning (5)
      albumFunction expects (sarahMcLaughlinAlbum) returning (4)
      albumFunction expects (billyJoelAlbum) returning (5)

      ((styxAlbum :: sarahMcLaughlinAlbum :: billyJoelAlbum :: Nil) map albumFunction) must be(5 :: 4 :: 5 :: Nil)
    }



//    it("can mock even the nastiest things, like a final class") {
//
//      val album = mock[Album]
//      val track = mock[Track]
//
//      album.expects.tracks returning (Some(List(track)))
////      track.expects.name returning "Whip it"
//
//      val jukebox = new JukeBox(Some(List(album)))
//      jukebox.play
////      jukebox.currentTrack.get.name must be ("Whip it")
//    }

    //    it("can mock even the nastiest things, like a final class") {
    //      val compilationAlbum: CompilationAlbum = mock[CompilationAlbum]
    //      val artist1 = mock[Artist]
    //      val artist2 = mock[Artist]
    //      val artist3 = mock[Artist]
    //      val artist4 = mock[Artist]
    //
    //      val track1 = mock[Track]
    //      val track2 = mock[Track]
    //      val track3 = mock[Track]
    //      val track4 = mock[Track]
    //
    //
    //      compilationAlbum.expects.tracksAndActs returning (
    //        List((track1, List(artist1)),
    //          (track2, List(artist2)),
    //          (track3, List(artist3)),
    //          (track4, List(artist4))))
    //
    //      inSequence {
    //        (artist1.expects.lastName) returning ("Brubeck") //Dave Brubeck
    //        (artist2.expects.lastName) returning ("James") //Etta James
    //        (artist3.expects.lastName) returning ("DaHousecat") //Felix DaHousecat
    //        (artist4.expects.lastName) returning ("Allen") //Lily Allen
    //      }
    //
    ////      compilationAlbum.toArtisticString must be ("Brubeck | James | DaHousecat | Allen")
    //
    //      compilationAlbum.tracksAndActs.flatMap(_._2 map (_.asInstanceOf[Artist].lastName)).mkString(", ") must be("Brubeck, James, DaHousecat, Allen")
    //    }
  }

}
