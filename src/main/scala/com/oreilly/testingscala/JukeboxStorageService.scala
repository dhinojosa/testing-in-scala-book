package com.oreilly.testingscala


class JukeboxStorageService(dao:DAO) {
  def persist(jukeBox:JukeBox)  {
    jukeBox.albums.getOrElse(Nil).foreach{
      album => dao.persist(album)
      album.acts.foreach(act => dao.persist(act))
    }
  }
}