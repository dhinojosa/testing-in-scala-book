package com.oreilly.testingscala

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.scalacheck.Prop._
import com.oreilly.testingscala.AlbumGen._
import org.scalacheck.{Arbitrary, Gen, Prop}

class Specs2WithScalaCheck extends Specification with ScalaCheck {

  "Using Specs2 With ScalaCheck".title ^
  "Can be used with the check method" ! usePlainCheck
  "Can be used with constraints" ! useCheckWithConstraints
  "Can be used with generators" ! useGenerators
  "Can be used with Arbitrary in the same way" ! useArbitrary
  "Can be used with Arbitrary in a clever way" ! useAnArbitraryInACleverWay
//  "Can be used with Labelling" ! useLabelling


  //This is a workaround
//  implicit val foo3: (Unit => Prop) = (x: Unit) => Prop(Prop.Result(Prop.True))

  def usePlainCheck = check((x: Int, y: Int) => {
    (x + y) must be_==(y + x)
  })

  def useCheckWithConstraints = forAll((x: Int, y: Int) => ((x > 10)) ==> {
      (x + y) must be_== (y + x)
    }
  )

  def useGenerators = forAll(Gen.containerOfN[Set, Int](4, Gen.choose(20, 60))) {
    x => x.size must be_<= (4) and (x.sum must be_< (240))
  }

  def useArbitrary = check((album: Album) => album.ageFrom(2012) must be_==(2012 - album.year))


  val mapIntString = Arbitrary {
    for {
      y <- Gen.alphaStr
      x <- Gen.choose(3, 300)
    } yield Map(x -> y)
  }

  def useAnArbitraryInACleverWay = mapIntString {
    (x: Map[Int, String]) => x.size must be_==(1)
  }

//  def useLabelling = forAll {
//    (x: Int, y: Int) => (x > 0 && y > 0) ==> {
//        (x + y == y + x) :| "All Equal" &&
//        (x + y >= y)     :| "Greater Than Failed" &&
//        (x + y >= x)     :| "Less Than Failed"
//    }
//  }
}