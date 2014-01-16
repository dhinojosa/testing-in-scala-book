package com.oreilly.testingscala


case class GrammyAlbumAward(category: String, album: Album)

object GrammyAlbumAward {
  def bestJazzInstrumentalAlbum = new GrammyAlbumAward("Jazz Instrumental",
    new Album("Forever", 2011, new Band("Corea, Clarke, and White",
      new Artist("Chick", "Corea"),
      new Artist("Stanley", "Clarke"),
      new Artist("Lenny", "White"))))
}
