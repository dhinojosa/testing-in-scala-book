package com.oreilly.testingscala

import org.specs2.Specification
import org.specs2.matcher.DataTables

class AlbumAgeDataTableSpecification extends Specification with DataTables {def is =
  "Trying out a table of values for testing purposes to determine the age of albums".title ^
  """The first column is the album name, the second is a band name,
     and third is a year, and the fourth is the age from the year 2070""" ! ageTable

  def ageTable =
    "Album Name"          | "Band Name"                 | "Year" | "Age" |
    "Under the Iron Sea"  !! "Keane"                    ! 2006   !   64  |
    "Rio"                 !! "Duran Duran"              ! 1982   !   88  |
    "Soul Revolution"     !! "Bob Marley & the Wailers" ! 1971   !   99  |> {
      (a:String, b:String, c:Int, d:Int) ⇒ new Album(a, c, new Band(b)).ageFrom(2070) must_== d
    }
}
