package com.oreilly.testingscala

import org.scalacheck.Prop.classify
import org.scalacheck.{Arbitrary, Gen, Prop, Properties}
import org.scalacheck.Prop._


object VariousGenCheckProperties extends Properties("Various Gen Properties") {
  //This is a workaround. Should disappear
  implicit val foo: (Unit => Prop) = (x: Unit) => Prop(Prop.Result(Prop.True))

  property("Gen.value chooses the exact value") = Prop.forAll(Gen.value("Orinoco Flow")) {
    _ == "Orinoco Flow"
  }

  property("Gen.choose chooses from between a list of values") =
    Prop.forAll(Gen.choose(1, 52)) {
      card => card < 53 && card > 0
    }

  property("Gen.oneOf chooses one from a list..") =
    Prop.forAll(Gen.oneOf(Gen.choose(0, 3), Gen.value("Aretha Franklin"))) {
      _ match {
        case y: Int => (0 to 3).contains(y)
        case z: String => z == "Aretha Franklin"
      }
    }

  property("Gen.pick chooses a given number of elements randomly..") =
    Prop.forAll(Gen.pick(3, List("Quiet Riot",
      "Foreigner",
      "Twisted Sister",
      "Motley Crue",
      "Scorpions",
      "Poison",
      "Ratt"))) {
      x => println(x)
    }

  property("Gen.ListOfN (Always of size N") =
    Prop.forAll(Gen.listOfN(4, Gen.choose(20, 60))) {
      x => println(x); (x.size == 4) && (x.sum < 240)
    }

  property("Gen.ListOf (Random)") =
    Prop.forAll(Gen.listOf(Gen.choose(20, 60))) {
      x =>
       if (x.size > 0) x(0) > 19 && x(0) < 61
       else true
    }

  property("Gen.ListOf (Random) and classified") =
    Prop.forAll(Gen.listOf(Gen.choose(20, 60))) {
      x =>
        classify((x.size >= 0) && (x.size < 50), "0 to 50") {
          classify((x.size >= 50) && (x.size < 100), "50 - 100") {
            classify((x.size >= 100), "100 or more") {
              true
            }
          }
        }
    }

  property("Gen.ListOf1 (At least one element)") =
    Prop.forAll(Gen.listOf1(Gen.choose(20, 60))) {
      _.size > 0
    }

  property("Gen.containerOf any type of container (Random Size)") =
    Prop.forAll(Gen.containerOf[Set, Int](Gen.choose(1, 5))) {
      x ⇒ true
    }

  property("Gen.containerOf1") =
    Prop.forAll(Gen.containerOf1[Set, Int](Gen.choose(1, 5))) {
      _.size > 0
    }

  property("Gen.containerOfN") =
    Prop.forAll(Gen.containerOfN[Set, Int](4, Gen.choose(20, 60))) {
      x => println(x); (x.size <= 4) && (x.sum < 240)
    }


  property("Gen.resultOf") =
    Prop.forAll(Gen.resultOf((x: String, y: Int) => Map(x -> y))) {
      p =>  {println(p); true}
    }

  property("Gen.resultOf") =
    Prop.forAll(Gen.resultOf((x: String, y: Int, a: String, b: String) =>
      new Album(x, y, new Artist(a, b)))) {
      a => true
    }

  property("Gen.frequency") =
    Prop.forAll(Gen.frequency(
      (3, Gen.value("Phoenix")),
      (2, Gen.value("LCD Soundsystem")),
      (5, Gen.value("JJ")))) {

      x =>
        classify(x == "Phoenix", "Phoenix") {
          classify(x == "LCD Soundsystem", "LCD Soundsystem") {
            classify(x == "JJ", "JJ") {
              true
            }
          }
        }
    }

  property("Gen.sized") =
    Prop.forAll(Gen.sized(x => Gen.listOfN(x, Gen.value("*")))) {
      x => println(x.size + " " + x); true
    }

  //  Property created to fail; Uncomment to run
  //  property("Compound assertions without labels") =
  //    Prop.forAll {
  //      (x:Int, y:Int) => (x > 0 && y > 0) ==> {
  //          (x + y) != 0 && (x+y) > 0 && (x+y) < (x+y)
  //      }
  //    }

  //  Property created to fail; Uncomment to run
  //  property("Compound assertions with labels") =
  //      Prop.forAll {
  //        (x: Int, y: Int) => (x > 0 && y > 0) ==> {
  //            ((x + y) != 0)   :| "(x+y) equals (y+x)" &&
  //            ((x+y) > 0)      :| "(x+y) > 0" &&
  //            ((x+y) < (x+y))  :| "(x+y) < (x+y)"
  //        }
  //      }

  //  Property created to fail; Uncomment to run
  //  property("Compound assertions with labels in reverse") =
  //        Prop.forAll {
  //          (x: Int, y: Int) => (x > 0 && y > 0) ==> {
  //            ("(x+y) equals (y+x)" |: ((x + y) != 0))  &&
  //            ("(x+y) > 0"          |: ((x+y) > 0))     &&
  //            ("(x+y) < (x+y)"      |: ((x+y) < (x+y)))
  //          }
  //        }

  //  Property created to fail; Uncomment to run
  //  property("Compound assertion labelling with evidence") =
  //    Prop.forAll {
  //      (x: Int, y: Int) => ((x > 0) && (y > 0)) ==> {
  //        val result = x + y  //intermediate result
  //          ("result = " + result) |: all(
  //          ("(x+y) equals (y+x)"  |: (result != 0))    &&
  //          ("(x+y) > 0"           |: (result > 0))     &&
  //          ("(x+y) < (x+y)"       |: (result < result))
  //        )
  //      }
  //    }


  property("Gen. using a Map") = {
    val gen = for {
      y <- Gen.alphaStr
      x <- Gen.choose(3, 300)
    } yield Map(x -> y)

    Prop.forAll(gen) {
      x => true
    }
  }

  property("Gen. using a large Map") = {
    val entries = for {
      y <- Gen.alphaStr
      x <- Gen.choose(3, 300)
    } yield (x -> y)

    val maps = for {
      x <- Gen.listOfN(4, entries)
    } yield (Map(x: _*))

    Prop.forAll(maps) {
      x => println(x)
    }
  }

  property("Gen. with custom objects") = {
    val albums = for {
      a <- Gen.alphaStr
      b <- Gen.choose(1900, 2012)
      c <- Gen.alphaStr
    } yield (new Album(a, b, new Band(c)))

    val listOfAlbums = Gen.listOf(albums)
    Prop.forAll(listOfAlbums) {
      albums =>
        val jukebox = new JukeBox(Some(albums))
        jukebox.albums.get.size == albums.size
    }
  }

  property("Arbitrary Creating Objects") = {
    implicit val album: Arbitrary[Album] = Arbitrary {
      for {
        a <- Gen.alphaStr
        b <- Gen.choose(1900, 2012)
        c <- Gen.alphaStr
      } yield (new Album(a, b, new Band(c)))
    }

    Prop.forAll {
      album: Album => album.ageFrom(2012) == (2012 - album.year)
    }
  }
}