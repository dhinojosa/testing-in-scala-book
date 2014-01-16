package com.oreilly.testingscala

import org.specs2.Specification
import org.mockito.Mockito._

class JukeboxStorageServiceMockitoAcceptanceSpec extends Specification {
  def is = {
    "You can use Mockito to perform Scala mocking" ! useMockitoToMockClasses
  }

  def useMockitoToMockClasses = {
    val daoMock = mock(classOf[DAO])

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

    //no replay

    //make the call
    jukeboxStorageService.persist(jukeBox)

    //verify that the calls expected were made
    verify(daoMock).persist(theGratefulDead)
    verify(daoMock).persist(workingmansDead)
    success
  }
}