package com.oreilly.testingscala

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers


class MustMatcherSpec extends FunSpec with MustMatchers {
  describe("Using all must matchers") {
    it("has simple matchers") {
      val list = 2 :: 4 :: 5 :: Nil
      list.size must be(3)
    }

    it("has string matchers") {
      val string = """I fell into a burning ring of fire.
         I went down, down, down and the flames went higher"""
      string must startWith("I fell")
      string must endWith("higher")
      string must not endWith "My favorite friend, the end"
      string must include("down, down, down")
      string must not include ("Great balls of fire")

      string must startWith regex ("I.fel+")
      string must endWith regex ("h.{4}r")
      string must not endWith regex("\\d{5}")
      string must include regex ("flames?")

      string must fullyMatch regex ("""I(.|\n|\S)*higher""") //regex in triple quotes
    }

    it("has <, >, <=, >=, === matchers") {
      val answerToLife = 42
      answerToLife must be < (50)
      answerToLife must not be >(50)
      answerToLife must be > (3)
      answerToLife must be <= (100)
      answerToLife must be >= (0)
      answerToLife must be === (42)
      answerToLife must not be ===(400)
    }

    it("has theSameInstanceAs") {
      val garthBrooks = new Artist("Garth", "Brooks")
      val chrisGaines = garthBrooks
      val debbieHarry = new Artist("Debbie", "Harry")
      garthBrooks must be theSameInstanceAs (chrisGaines)
      garthBrooks must not be theSameInstanceAs(debbieHarry)
    }

    it("has checking floating point imperfections") {
      (4.0 + 1.2) must be(5.2)
      (0.9 - 0.8) must be(0.1 plusOrMinus .01)
      (0.4 + 0.1) must not be (40.00 plusOrMinus .30)
    }

    it("has methods for iterable") {
      List() must be('empty)
      1 :: 2 :: 3 :: Nil must contain(3)
    }

    it("has methods for seq") {
      (1 to 9).toList must have length (9)
    }

    it("has methods for traversable") {
      (20 to 60 by 2).toList must have size (21)
    }

    it("has methods for map") {
      val map = Map("Jimmy Page" -> "Led Zeppelin", "Sting" -> "The Police", "Aimee Mann" -> "Til\' Tuesday")
      map must contain key ("Sting")
      map must contain value ("Led Zeppelin")
      map must not contain key("Brian May")
    }

    it("has method for java collections") {
      import java.util.{List => JList, ArrayList ⇒ JArrayList,
      Map ⇒ JMap, HashMap ⇒ JHashMap}

      val jList: JList[Int] = new JArrayList[Int](20)
      jList.add(3);
      jList.add(6);
      jList.add(9)
      val emptyJList: JList[Int] = new JArrayList[Int]()
      emptyJList must be('empty)
      jList must have length (3)
      jList must have size (3)
      jList must contain(6)
      jList must not contain (10)

      val backupBands: JMap[String, String] = new JHashMap()
      backupBands.put("Joan Jett", "Blackhearts")
      backupBands.put("Tom Petty", "Heartbreakers")

      backupBands must contain key ("Joan Jett")
      backupBands must contain value ("Heartbreakers")

      backupBands must not contain key("John Lydon")
    }

    it("has compound and and or") {
      val redHotChiliPeppers = List("Anthony Kiedis", "Flea", "Chad Smith", "Josh Klinghoffer")
      redHotChiliPeppers must (contain("Anthony Kiedis") and
        (not contain ("John Frusciante")
          or contain("Dave Navarro")))
    }

    it("has the ability to check properties") {
      import scala.collection.mutable.WrappedArray
      val album = new Album("Blizzard of Ozz", 1980, new Artist("Ozzy", "Osbourne"))
      album must have(
        'title("Blizzard of Ozz"),
        'year(1980),
        'acts(new WrappedArray.ofRef(List(new Artist("Ozzy", "Osbourne")).toArray))
      )
    }

    it("has Exception handling") {
      import scala.io.Source
      import java.net.ConnectException
      val exception = evaluating {
        Source.fromURL("http://www.dandelionsaregrowingtoofast.net").foreach(print)
      } must produce[ConnectException]
      exception.getClass must be(classOf[ConnectException])
    }
  }
}