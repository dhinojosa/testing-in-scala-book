package com.oreilly.testingscala

import org.specs2.mutable.Specification

class FakeDAO extends DAO {
  var map = Map[Long, Any]()
  var count = 0L

  def persist[T](t: T) {
    map = map + (count -> t)
    count = count + 1
  }

  def persistCount = map.size
}

class UsingFakeUnitSpec extends Specification {
  "When two albums are added to the fake DAO the albums should be stored" in {
    val dao = new DAOStub
    dao.persist(new Album("The Roaring Silence", 1976, new Band("Manfred Mann's Earth Band")))
    dao.persist(new Album("Mechanical Animals", 1998, new Artist("Marilyn", "Manson")))
    dao.persistCount must be_==(2)
  }
}
