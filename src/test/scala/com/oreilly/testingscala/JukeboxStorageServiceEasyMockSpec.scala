package com.oreilly.testingscala

import org.scalatest.matchers.MustMatchers
import org.scalatest.{FunSpec, Spec}
import org.easymock.EasyMock._

class JukeboxStorageServiceEasyMockSpec extends FunSpec with MustMatchers {
  describe("A Jukebox Storage Service") {
    it("should use easy mock to mock out the DAO classes") {
      val daoMock = createMock(classOf[DAO])

      //set up actual values to be used.
      val theGratefulDead: Band = new Band("Grateful Dead")
      val wyntonMarsalis: Artist = new Artist("Wynton", "Marsalis")
      val psychedelicFurs: Band = new Band("Psychedelic Furs")
      val ericClapton: Artist = new Artist("Eric", "Clapton")

      val workingmansDead = new Album("Workingman's Dead", 1970, None, theGratefulDead)
      val midnightToMidnight = new Album("Midnight to Midnight", 1987, None, psychedelicFurs)
      val wyntonAndClapton = new Album("Wynton Marsalis and Eric Clapton play the Blues", 2011, None,
        wyntonMarsalis, ericClapton)

      val jukeBox = new JukeBox(Some(List(workingmansDead, midnightToMidnight, wyntonAndClapton)))

      //create the subject under test
      val jukeboxStorageService = new JukeboxStorageService(daoMock)

      //set up expectations
      daoMock.persist(workingmansDead)
      daoMock.persist(midnightToMidnight)
      daoMock.persist(wyntonAndClapton)

      daoMock.persist(theGratefulDead)
      daoMock.persist(psychedelicFurs)
      daoMock.persist(wyntonMarsalis)
      daoMock.persist(ericClapton)

      //replay, more like rewind
      replay(daoMock)

      //make the call
      jukeboxStorageService.persist(jukeBox)

      //verify that the calls expected were made
      verify(daoMock)
    }
  }
}