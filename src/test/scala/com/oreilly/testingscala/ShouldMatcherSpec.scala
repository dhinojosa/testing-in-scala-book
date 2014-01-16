package com.oreilly.testingscala

import org.scalatest.{FunSpec, Spec}
import org.scalatest.matchers.ShouldMatchers



class ShouldMatcherSpec extends FunSpec with ShouldMatchers {
  describe("Using all should matchers") {
    it("has simple matchers") {
      val list = 2 :: 4 :: 5 :: Nil
      list.size should be(3)
      list.size should equal(3)
    }

    it("has string matchers") {
      val string = """I fell into a burning ring of fire.
         I went down, down, down and the flames went higher"""
      string should startWith("I fell")
      string should endWith("higher")
      string should not endWith "My favorite friend, the end"
      string should include("down, down, down")
      string should not include ("Great balls of fire")

      string should startWith regex ("I.fel+")
      string should endWith regex ("h.{4}r")
      string should not endWith regex("\\d{5}")
      string should include regex ("flames?")

      string should fullyMatch regex ("""I(.|\n|\S)*higher""") //regex in triple quotes
    }

    it("has <, >, <=, >=, === matchers") {
      val answerToLife = 42
      answerToLife should be < (50)
      answerToLife should not be >(50)
      answerToLife should be > (3)
      answerToLife should be <= (100)
      answerToLife should be >= (0)
      answerToLife should be === (42)
      answerToLife should not be ===(400)
    }

    it("has theSameInstanceAs") {
      val garthBrooks = new Artist("Garth", "Brooks")
      val chrisGaines = garthBrooks

      garthBrooks should be theSameInstanceAs (chrisGaines)

      val debbieHarry = new Artist("Debbie", "Harry")
      garthBrooks should not be theSameInstanceAs(debbieHarry)
    }

    it("has checking floating point imperfections") {
      (4.0 + 1.2) should be(5.2)
      (0.9 - 0.8) should be(0.1 plusOrMinus .01)
      (0.4 + 0.1) should not be (40.00 plusOrMinus .30)
    }

    it("has methods for iterable") {
      List() should be('empty)
      8 :: 6 :: 7 :: 5 :: 3 :: 0 :: 9 :: Nil should contain(7)
    }

    it("has methods for seq") {
      (1 to 9).toList should have length (9)
    }

    it("has methods for traversable") {
      (20 to 60 by 2).toList should have size (21)
    }

    it("has methods for map") {
      val map = Map("Jimmy Page" -> "Led Zeppelin", "Sting" -> "The Police", "Aimee Mann" -> "Til\' Tuesday")
      map should contain key ("Sting")
      map should contain value ("Led Zeppelin")
      map should not contain key("Brian May")
    }

    it("has method for java collections") {
      import java.util.{List => JList, ArrayList ⇒ JArrayList,
      Map ⇒ JMap, HashMap ⇒ JHashMap}

      val jList: JList[Int] = new JArrayList[Int](20)
      jList.add(3);
      jList.add(6);
      jList.add(9)
      val emptyJList: JList[Int] = new JArrayList[Int]()
      emptyJList should be('empty)
      jList should have length (3)
      jList should have size (3)
      jList should contain(6)
      jList should not contain (10)

      val backupBands: JMap[String, String] = new JHashMap()
      backupBands.put("Joan Jett", "Blackhearts")
      backupBands.put("Tom Petty", "Heartbreakers")

      backupBands should contain key ("Joan Jett")
      backupBands should contain value ("Heartbreakers")

      backupBands should not contain key("John Lydon")
    }

    it("has compound and and or") {
      val redHotChiliPeppers = List("Anthony Kiedis", "Flea", "Chad Smith", "Josh Klinghoffer")

      redHotChiliPeppers should (contain("Anthony Kiedis") and
        (not contain ("John Frusciante")
          or contain("Dave Navarro")))

      redHotChiliPeppers should not (contain ("The Edge") or contain ("Kenny G"))

      //redHotChiliPeppers should not contain "The Edge" or contain "Kenny G"

      var total = 3
      redHotChiliPeppers should not (contain ("The Edge") or contain {total += 6; "Kenny G"})
      total should be (9)

//      val gorillaz:List[String] = null;
//      gorillaz should not be (null)
//      gorillaz should contain ("Damon Albarn")

    }



    it ("has the ability to check properties") {
      import scala.collection.mutable.WrappedArray
      val album = new Album("Blizzard of Ozz", 1980, new Artist("Ozzy", "Osbourne"))
      album should have (
         'title ("Blizzard of Ozz"),
         'year (1980),
         'acts (new WrappedArray.ofRef(List(new Artist("Ozzy", "Osbourne")).toArray))
      )
    }
  }
}