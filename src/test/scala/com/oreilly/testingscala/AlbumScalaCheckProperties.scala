package com.oreilly.testingscala

import org.scalacheck.{Prop, Properties}
import org.scalacheck.Prop._

object AlbumScalaCheckProperties extends Properties("Album Creation") {
  property("album can be created using a year from 1900 to 3000") =
    Prop.forAll {
      (title: String, year: Int, firstName: String, lastName: String) =>
        (year > 1900 && year < 3000) ==> {
          val album = new Album(title, year, new Artist(firstName, lastName))
          album.year == year
          album.title == title
        }
    }
}