package com.oreilly.testingscala

trait DAO {
  def persist[T](t: T)
}


object DAO {

  private class MySqlDAO extends DAO {
    def persist[T](t: T) {}
  }

  private class DB2DAO extends DAO {
    def persist[T](t: T) {}
  }

  def createMySqlDAO: DAO = new MySqlDAO

  def createDB2DAO: DAO = new DB2DAO
}



