package com.oreilly.testingscala

import org.specs2.mutable.Specification
import org.specs2.matcher.Matcher

class MatchersUnitSpec extends Specification {
  "Matchers in Specs2 " should {
    """have boolean matchers""" in {
      val rumours = new Album("Rumours", 1977,
        Some(List(new Track("Second Hand News", "2:43"),
          new Track("Dreams", "4:14"),
          new Track("Never Going Back Again", "2:02"),
          new Track("Don't Stop", "3:11"))), new Band("Fleetwood Mac"))

      rumours.title must be_==("Rumours")
      rumours.title must beEqualTo("Rumours")
      rumours.title must_== ("Rumours")
      rumours.title mustEqual "Rumours"
      rumours.title should_== "Rumours"
      rumours.title === "Rumours"
      rumours.title must be equalTo ("Rumours")

      rumours.title must not be equalTo("Sweet Emotion")
      rumours.title must_!= "Sweet Emotion"
      rumours.title mustNotEqual "Sweet Emotion"
      rumours.title must be_!=("Sweet Emotion")
      rumours.title !== "Sweet Emotion"
    }

    "have string matchers" in {
      val boston = new Album("Boston", 1976,
        Some(List(new Track("More Than a Feeling", "4:44"),
          new Track("Peace of Mind", "5:02"),
          new Track("Foreplay/Long Time", "7:47"),
          new Track("Rock and Roll Band", "2:59"))), new Band("Boston"))

      val rumours = new Album("Rumours", 1977,
        Some(List(new Track("Second Hand News", "2:43"),
          new Track("Dreams", "4:14"),
          new Track("Never Going Back Again", "2:02"),
          new Track("Don't Stop", "3:11"))), new Band("Fleetwood Mac"))

      boston.title must beEqualTo("BoSTon").ignoreCase
      boston.title must beEqualTo(" Boston").ignoreSpace
      boston.title must beEqualTo(" BoStOn  ").ignoreSpace.ignoreCase
      boston.title must contain("os")
      boston.title must startWith("Bos")
      boston.title must endWith("ton")
      boston.title must not startWith ("Char")
      boston.title must have size (6)
      boston.title must beMatching("B\\w{4}n")
      boston.title must beMatching("""B\w{4}n""")
      boston.title must =~("""B\w{4}n""")
      boston.title must find("""(os.)""").withGroups("ost")
    }

    "have relational operator matchers" in {
      val answerToLife = 42
      answerToLife should be_<(50)
      answerToLife must beLessThan(50)
      answerToLife should not(be_>(50))
      answerToLife should not(beGreaterThan(50))
      answerToLife should be_>(3)
      answerToLife must beGreaterThan(3)
      answerToLife should be_<=(100)
      answerToLife must beLessThanOrEqualTo(100)
      answerToLife should be_>=(0)
      answerToLife must beGreaterThanOrEqualTo(0)
      answerToLife === (42)
    }

    "have checking floating point imperfections" in {
      (4.0 + 1.2) must be_==(5.2)
      (0.9 - 0.8) must beCloseTo(0.1, .01)
      (0.4 + 0.1) must not(beCloseTo(40.00, .30))
    }

    "have theSameInstanceAs" in {
      val garthBrooks = new Artist("Garth", "Brooks")
      val chrisGaines = garthBrooks

      garthBrooks must beTheSameAs(chrisGaines)

      val debbieHarry = new Artist("Debbie", "Harry")
      garthBrooks must not beTheSameAs (debbieHarry)
    }

    "have exception handling" in {
      {
        {
          new Album("A Cheesy Christmas", 1000)
        } must throwAn[IllegalArgumentException]
      }
    }

    "have iterable matchers" in {
      (List() must be).empty
      (Nil must be).empty
      (8 :: 6 :: 7 :: 5 :: 3 :: 0 :: 9 :: Nil must not) be empty
      (8 :: 6 :: 7 :: 5 :: 3 :: 0 :: 9 :: Nil) must contain(7)
      List(4, 5, 6) must not contain(7, 8, 9)
      List(8, 6, 7, 5, 3, 0, 9) must contain(5, 3, 0).inOrder
      List(3, 0, 9) must contain(3, 0, 9).only.inOrder
    }

    "have seq and traversable matchers" in {
      (1 to 9).toList must have length (9)
      (20 to 60 by 2).toList must have size (21)
    }

    "have custom matchers" in {
      def beEven: Matcher[Int] = (i: Int) => (i % 2 == 0, i+" is even", i+" is odd")
      def beCapitalizedAs(capitalized: String) = be_==(capitalized) ^^ ((_:String).toUpperCase)

      4 must beEven
      "Flandd" must beCapitalizedAs("FLAN")
    }

    //    (Nil must be).empty
    //    List(1, 2, 3) must not be empty
    //    List(1, 2, 3) must contain(3)
    //    List(1, 2, 3) must not contain (5)
    //    List(4, 5, 6) must not contain(7, 8, 9)
    //    List(1, 2, 3, 4, 5, 6) must contain(3, 4, 5).inOrder
    //    List(4, 5, 6) must contain(4, 5, 6).only.inOrder
    //    List(1, 2) must have size (2)
    //    List(1, 2) must have length (2)
    //    List("Hello", "World") must containMatch("ll") // matches with .*ll.*
    //    List("Hello", "World") must containMatch("Hello") // matches with .*ll.*
    //    List("Hello", "World") must containPattern(".*llo") // matches with .*llo
    //    List("Hello", "World") must containPattern("\\w{5}")
    //    List("Hello", "World") must containMatch("ll").onlyOnce
    //    List("Hello", "World") must have(_.size >= 5)
    //    List("Hello", "World") must haveTheSameElementsAs(List("World", "Hello"))
    //  }

    "have map matchers" in {
      val map = Map("Jimmy Page" -> "Led Zeppelin", "Sting" -> "The Police", "Aimee Mann" -> "Til\' Tuesday")
      map must haveKey("Sting")
      map must haveValue("Led Zeppelin")
      map must not haveKey ("Brian May")
      map must havePair("Aimee Mann" -> "Til\' Tuesday")
    }


    "have partial function matchers" in {
      val goldPartialFunction: PartialFunction[Int, String] = new PartialFunction[Int, String] {
            //States that this partial function will take on the task
            def isDefinedAt(x: Int) = x >= 500000

            //What we do if this does partial function matches
            def apply(v1: Int) = "Gold"
          }

      val platinumPartialFunction: PartialFunction[Int, String] = {case x: Int if (x >= 1000000) => "Platinum"}
      val junkPartialFunction: PartialFunction[Int, String] = {case x: Int if (x < 500000) => "Alternative"}

      val riaaCertification = goldPartialFunction orElse platinumPartialFunction orElse junkPartialFunction
      riaaCertification must beDefinedAt (100)
      riaaCertification must beDefinedBy (100 -> "Alternative")
    }

//    "has compound and and or" in  {
//      val redHotChiliPeppers = List("Anthony Kiedis", "Flea", "Chad Smith", "Josh Klinghoffer")
//      ((redHotChiliPeppers must contain("Anthony Kiedis")) and not contain ("John Frusciante")) or contain("Dave Navarro")
//    }

    "has xml matching" in {
      val coldPlayAlbums = <albums>
          <album name="Parachutes"/>
          <album name="A Rush of Blood to the Head"/>
        <album name="X&amp;Y"/>
        <album name="Viva la Vida or Death and All His Friends"/>
          <album name="Mylo Xyloto"/>
      </albums>

      coldPlayAlbums must beEqualToIgnoringSpace(<albums>
          <album name="Parachutes"/>
          <album name="A Rush of Blood to the Head"/>
        <album name="X&amp;Y"/>
        <album name="Viva la Vida or Death and All His Friends"/>
          <album name="Mylo Xyloto"/>
      </albums>)

      coldPlayAlbums must ==/(<albums>
          <album name="Parachutes"/>
          <album name="A Rush of Blood to the Head"/>
        <album name="X&amp;Y"/>
        <album name="Viva la Vida or Death and All His Friends"/>
          <album name="Mylo Xyloto"/> </albums>) //==/ is an alias for matching ignoring space


      coldPlayAlbums must ==/(<albums>
          <album name="Parachutes"/>
        <album name="X&amp;Y"/>
        <album name="A Rush of Blood to the Head"/>

          <album name="Viva la Vida or Death and All His Friends"/>
          <album name="Mylo Xyloto"/> </albums>).ordered
    }
  }
}