package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{GivenWhenThen, FreeSpec}

class JukeboxFreeSpec extends FreeSpec with ShouldMatchers with GivenWhenThen {

  "Given 3 albums" - {
    val badmotorfinger = new Album("Badmotorfinger", 1991, None, new Band("Soundgarden"))
    val thaDoggFather = new Album("The Dogg Father", 1996, None, new Artist("Snoop Doggy", "Dogg"))
    val satchmoAtPasadena = new Album("Satchmo At Pasadena", 1951, None, new Artist("Louis", "Armstrong"))
    "when a juke box is instantiated it should accept some albums" - {
      val jukebox = new JukeBox(Some(List(badmotorfinger, thaDoggFather, satchmoAtPasadena)))
      "then a jukebox's album catalog size should be 3" in {
        jukebox.albums.get should have size (3)
      }
    }
  }

  "El constructor de Jukebox puedo aceptar la palabra clave de 'None'" - {
    val jukebox = new JukeBox(None)
    "y regresas 'None' cuando llamado " in {
      jukebox.albums should be(None)
    }
  }

  "Given three albums where one album is by Hall and Oates" - {
    "A jukebox should throw a NonApprovedActException" is (pending)
  }

  "If a jukebox has 1 or more albums, the jukebox is ready to play" in {
    given("1 or more albums")
    val badmotorfinger = new Album("Badmotorfinger", 1991, None, new Band("Soundgarden"))
    val thaDoggFather = new Album("The Dogg Father", 1996, None, new Artist("Snoop Doggy", "Dogg"))
    val satchmoAtPasadena = new Album("Satchmo At Pasadena", 1951, None, new Artist("Louis", "Armstrong"))

    and("a jukebox instantiated with those albums")
    val jukebox = new JukeBox(Some(List(badmotorfinger, thaDoggFather, satchmoAtPasadena)))

    when("jukebox.readyToPlay is called")
    val result = jukebox.readyToPlay

    then("result should be true")
    result should be(true)
  }

  "Given no albums" - {
    "when we instantiate a jukebox" - {
      val jukebox = new JukeBox(None)
      "then readyToPlay" - {
        "should return false" in {
            jukebox.readyToPlay should be (false)
        }
      }
    }
  }

  //Ignoring a test
  "Given 1000 album" -{
    "when we instantiate a jukebox" - {
      "then ready to play" -{
        "should return true" ignore {
          val album1 = new Album("Electric Mud", 1968)
          val album2= new Album("King Bee", 1981)
          //May not go through with this test
        }
      }
    }
  }
}