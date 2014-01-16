package com.oreilly.testingscala

import org.specs2.mutable.Specification


class DAOStub extends DAO {
  var count = 0

  def persist[T](t: T) {
    count = count + 1
  }

  def persistCount = count
}

class DAOStubWithExceptionAfterTheFirstPersist extends DAO {
  var alreadyCalledOnce = false

  def persist[T](t: T) {
    if (alreadyCalledOnce) throw new RuntimeException("Unable to store")
    alreadyCalledOnce = true
  }
}

class UsingStubUnitSpec extends Specification {

  "Create 2 albums that we will persist to a stub DAO" in {
    val dao = new DAOStub
    dao.persist(new Album("The Roaring Silence", 1976, new Band("Manfred Mann's Earth Band")))
    dao.persist(new Album("Mechanical Animals", 1998, new Artist("Marilyn", "Manson")))
    dao.persistCount must be_==(2)
  }
}


class UsingStubWithExceptionUnitSpec extends Specification {

  "Create 2 albums that we will persist to a stub DAO" in {
    val dao = new DAOStubWithExceptionAfterTheFirstPersist
    dao.persist(new Album("The Roaring Silence", 1976, new Band("Manfred Mann's Earth Band")))
    dao.persist(new Album("Mechanical Animals", 1998, new Artist("Marilyn", "Manson"))) must throwA[RuntimeException]
  }
}
