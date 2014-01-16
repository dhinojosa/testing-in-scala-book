package com.oreilly.testingscala

import org.scalacheck.{Prop, Properties}

object BasicScalaCheckProperties extends Properties("Simple Math"){
 // property("Sum is greater than it's parts") = Prop.forAll {(x:Int, y:Int) => x+y > x && x+y > y}

  property("Sums are associative") = Prop.forAll {
    (x:Int, y:Int) => x+y == y+x
  }
}