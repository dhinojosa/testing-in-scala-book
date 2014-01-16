package com.oreilly.testingscala

class AlbumMultipleStorageService {
   val mysqlDAO = DAO.createMySqlDAO
   val db2DAO = DAO.createDB2DAO

   def persist(album:Album) {
     mysqlDAO.persist(album)
     db2DAO.persist(album)
     
     album.acts.foreach{act => mysqlDAO.persist(act); db2DAO.persist(act)}
   }
}
