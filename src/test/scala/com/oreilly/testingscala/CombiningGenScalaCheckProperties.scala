package com.oreilly.testingscala

import org.scalacheck.{Gen, Prop, Properties}
import org.scalacheck.Prop._

object CombiningGenScalaCheckProperties extends Properties("Combining Properties") {
  val stringsOnly = Prop.forAll(Gen.alphaStr.suchThat(_ != "")) {
    x: String => x.size >= 0
  }

  val positiveNumbersOnly = Prop.forAll(Gen.posNum[Int]) {
    x: Int => x >= 0
  }
  val positiveNumbers2Only = Prop.forAll(Gen.posNum[Int]) {
    x: Int => x > 0
  }

  val alwaysPass = Prop.forAll {
    x: Int => true
  }

  val wontPass = Prop.forAll((x: Int, y: Int) => x + y > 0)

  property("And") = stringsOnly && positiveNumbersOnly
  property("Or") = stringsOnly || wontPass


  val albumsWithArtists = Prop.forAll(Gen.alphaStr, Gen.chooseNum(1900, 3000),
    Gen.alphaStr, Gen.alphaStr) {
    (albumName: String, year: Int, firstName: String, lastName: String) =>
      val album = new Album(albumName, year, None, new Artist(firstName, lastName))
      album.title == albumName && album.year == year
  }

  val albumsWithBand = Prop.forAll(Gen.alphaStr, Gen.chooseNum(1900, 3000),
                                        Gen.alphaStr) {
    (albumName: String, year: Int, name: String) =>
      val album = new Album(albumName, year, None, new Band(name))
      album.title == albumName && album.year == year
  }

  property("Albums work") = albumsWithBand && albumsWithArtists
}