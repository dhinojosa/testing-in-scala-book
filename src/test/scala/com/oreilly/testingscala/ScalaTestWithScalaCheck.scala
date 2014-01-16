package com.oreilly.testingscala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

class ScalaTestWithScalaCheck extends FunSpec with ShouldMatchers with GeneratorDrivenPropertyChecks {
  describe("We can use test data from Scala check") {
    it("runs the same but with different constucts") {
      forAll {
        (a: Int, b: Int) =>
          (a + b) should be(b + a)
      }
    }

    it("runs constraints but differently") {
      forAll {
        (a: Int, b: Int) =>
          whenever(b > 14) {
            (a + b) should be(b + a)
          }
      }
    }

    //Uncomment to run tests ---- They will fail.
//        it("no need for test labels") {
//           forAll {
//             (x: Int, y: Int) =>
//               whenever (x > 0 && y > 0) {
//                 (x + y) should (not be (0) and ((be > 0) and (be < (x+y))))
//             }
//           }
//        }
//
//        it("runs with labels") {
//          forAll ("x", "y") {
//            (x: Int, y: Int) =>
//              whenever (x > 0 && y > 0) {
//                (x + y) should (not be (0) and ((be > 0) and (be < (x+y))))
//              }
//          }
//        }

    it("runs with generators") {
      forAll(Gen.choose(10, 20), Gen.choose(30, 40)) {
        (a: Int, b: Int) =>
          (a + b) should be(a + b)
      }
    }

    it("runs with classifiers") {
      forAll(Gen.choose(10, 20), Gen.choose(30, 40)) {
        (a: Int, b: Int) =>
          (a + b) should be(a + b)
      }
    }
    it("runs with labels and generators") {
      forAll((Gen.choose(10, 20), "a"), (Gen.choose(30, 40), "b")) {
        (a: Int, b: Int) =>
          (a + b) should be (a + b)
      }
    }

    it("of course you can use custom object") {
      import com.oreilly.testingscala.AlbumGen._
      forAll {
        (a: Album, b: Album) =>
          a.title should not be (b.title + "ddd")
      }
    }
  }
}