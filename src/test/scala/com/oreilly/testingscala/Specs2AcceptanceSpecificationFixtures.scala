package com.oreilly.testingscala

import org.specs2.Specification
import org.specs2.specification.Scope
import org.specs2.execute.Result
import org.joda.time.{DateMidnight, DateTime}


class Specs2WithoutFixtures extends Specification {
  def is =
    "Add an album to a shared list" ! test1 ^
      "Remove an album to a shared list" ! test2

  lazy val lst = scala.collection.mutable.Buffer(
    new Album("Fly By Night", 1974, new Band("Rush")),
    new Album("19", 2008, new Artist("Adele", "Laurie", "Adkins").withAlias("Adele")))

  def test1 = {
    lst.append(new Album("Prokofiev and Rachmaninoff: Cello Sonatas", 1991, new Artist("Yo", "Yo", "Ma")))
    lst must have size (3)
  }

  def test2 = lst.drop(1) must have size (2)
}


class Specs2WithoutFixturesButImmutable extends Specification {
  def is =
    "Add an album to a shared list" ! test1 ^
      "Remove an album to a shared list" ! test2

  lazy val lst = List(
    new Album("Fly By Night", 1974, new Band("Rush")),
    new Album("19", 2008, new Artist("Adele", "Laurie", "Adkins").withAlias("Adele")))

  def test1 = {
    val result = lst :+ new Album("Prokofiev and Rachmaninoff: Cello Sonatas", 1991, new Artist("Yo", "Yo", "Ma"))
    result must have size (3)
  }

  def test2 = lst.drop(1) must have size (1)
}


class Specs2WithTraits extends Specification {
  def is =
    "Add an album to a shared list" ! AddItemTest().test ^
      "Remove an album to a shared list" ! RemoveItemTest().test

  trait ListMaker {
    lazy val lst = scala.collection.mutable.Buffer(
      new Album("Fly By Night", 1974, new Band("Rush")),
      new Album("19", 2008, new Artist("Adele", "Laurie", "Adkins").withAlias("Adele")))
  }

  case class AddItemTest() extends ListMaker {
    def test = {
      lst.append(new Album("Prokofiev and Rachmaninoff: Cello Sonatas", 1991, new Artist("Yo", "Yo", "Ma")))
      lst must have size (3)
    }
  }

  case class RemoveItemTest() extends ListMaker {
    def test = lst.drop(1) must have size (1)
  }

}


class Specs2WithBeforeAndAfter extends Specification {
  def is =
    "Add an album to a shared list" ! AddItemTest().test ^
      "Remove an album to a shared list" ! RemoveItemTest().test

  trait ListMaker {
    lazy val lst = scala.collection.mutable.Buffer(
      new Album("Fly By Night", 1974, new Band("Rush")),
      new Album("19", 2008, new Artist("Adele", "Laurie", "Adkins").withAlias("Adele")))
  }

  case class AddItemTest() extends ListMaker {
    def test = {
      lst.append(new Album("Prokofiev and Rachmaninoff: Cello Sonatas", 1991, new Artist("Yo", "Yo", "Ma")))
      lst must have size (3)
    }
  }

  case class RemoveItemTest() extends ListMaker {
    def test = lst.drop(1) must have size (1)
  }


}


class ContextSpec extends org.specs2.mutable.Specification {
  "this is the first example" in new trees {
    tree.drop(2) must have size (2)
  }
  "this is the first example" in new trees {
    tree.drop(3) must have size (1)
  }
}

trait trees extends Scope {
  println("setup database")
  lazy val tree = List(1, 2, 3, 4)
}

object log extends org.specs2.specification.Around {
  def around[T <% Result](t: => T) = {
    println("Start process")
    val result: T = t
    println("End process")
    result
  }
}

object withCurrentDate extends org.specs2.specification.Outside[DateTime] {
  def outside = new DateTime
}

object withFakeDate extends org.specs2.specification.Outside[DateTime] {
  def outside = new DateMidnight(1980, 1, 2).toDateTime
}

class UsingTheAroundProcess extends Specification {
  def is =
    "this will log something before running" ! log(e1)

  lazy val lst = List(
    new Album("Storms of Life", 1986, new Artist("Randy", "Travis")),
    new Album("The Bad Touch", 1999, new Band("Bloodhound Gang")),
    new Album("Billie Holiday Sings", 1952, new Artist("Billie", "Holiday")))

  def e1 = {
    println("Running test")
    lst.drop(1) must have size (2)
  }
}

class UsingDates extends Specification {
  def is =
    "this will use the real date" ! (withCurrentDate(x => testDate(x)))
//     ^ "this will use a fake date" ! (withFakeDate(x => testDate(x))  Uncomment to view error

  def testDate(x: DateTime) = (x.plusDays(20).isAfterNow)
}

object logWithFakeDateTime extends org.specs2.specification.AroundOutside[DateTime] {
  def outside = new DateTime

  def around[T <% Result](t: ⇒ T) = {
    println(outside + ": Start process")
    val result = t
    println(outside + ": End process")
    result
  }
}

class UsingTheAroundOutsideProcess extends Specification {
  def is =
    "this will log something before running" ! logWithFakeDateTime(dateTime ⇒ e1(dateTime))

  def e1(dt: DateTime) = {
    lazy val lst = List(
      new Album("Storms of Life", 1986, new Artist("Randy", "Travis")),
      new Album("The Bad Touch", 1999, new Band("Bloodhound Gang")),
      new Album("Billie Holiday Sings", 1952, new Artist("Billie", "Holiday")))
    println("Running test at " + dt)
    lst.drop(1) must have size (2)
  }
}


