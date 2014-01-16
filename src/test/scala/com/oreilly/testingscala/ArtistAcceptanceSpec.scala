package com.oreilly.testingscala

import org.specs2.Specification

class ArtistAcceptanceSpec extends Specification { def is =
    "An artist should have a middle at construction"                                                         ^ t(3) ^
      """An artist should be able to be constructed with a Option[String] middle name and
         get it back calling 'middleName'""" ! makeAnArtistWithMiddleName                                    ^
                                                                                                             p^
      """An artist should be able to have a full name of the first and last name
         given a first and last name at construction time""" ! testFullNameWithFirstAndLast                  ^
      """An artist should be able to have a full name of the first, middle and last name
           given a first, middle, and last name at construction time""" ! testFullNameWithFirstMiddleAndLast ^
                                                                                                             endp^
    "An artist should have an alias"                                                                         ^
      """method called withAlias(String) that returns a copy of Artist with an alias""" ! testAlias


    def makeAnArtistWithMiddleName = {
      val vaughn = new Artist("Stevie", "Ray", "Vaughn")
      vaughn.middleName must be_==(Some("Ray"))
    }

    def testFullNameWithFirstAndLast = {
      val luther = new Artist("Luther", "Vandross")
      luther.fullName must be_==("Luther Vandross")
    }

    def testFullNameWithFirstMiddleAndLast = {
      val bonJovi = new Artist("Jon", "Bon", "Jovi")
      bonJovi.fullName must be_==("Jon Bon Jovi")
    }

    def testAlias = {
        val theEdge = new Artist("David", "Howell", "Evans").withAlias("The Edge")
        theEdge.alias must be_==(Some("The Edge"))
    }
}