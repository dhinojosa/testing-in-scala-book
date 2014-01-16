package com.oreilly.testingscala

object BruceSpringsteenFactory {
  private lazy val theBoss:Artist = {
    new Artist("Bruce", None, "Springsteen", List(
        new Album("Greetings from Asbury Park, N.J.", 1973, None, theBoss),
        new Album("The Wild, The Innocent\n& the E Street Shuffle", 1973, None, theBoss),
        new Album("Born To Run", 1975, None, theBoss),
        new Album("Darkness on the Edge of Town", 1978, None, theBoss),
        new Album("The River", 1980, None, theBoss),
        new Album("Nebraska", 1982, None, theBoss),
        new Album("Born in the USA", 1984, None, theBoss)), Some("The Boss"))
  }

  def artist = theBoss

  def discography = theBoss.getAlbums

  def jukebox = new JukeBox(Some(discography))
}