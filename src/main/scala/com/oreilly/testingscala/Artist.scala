package com.oreilly.testingscala

class Artist(val firstName: String, val middleName: Option[String], val lastName: String, val albums: List[Album], val alias: Option[String]) extends Act {

  def this(firstName: String, lastName: String) = this(firstName, None, lastName, Nil, None)

  def this(firstName: String, middleName: String, lastName: String) = this(firstName, Some(middleName), lastName, Nil, None)

  def getAlbums = albums

  def addAlbum(album: Album) = new Artist(firstName, middleName, lastName, album :: albums, alias)

  def fullName = middleName match {
    case Some(x) => firstName + " " + x + " " + lastName
    case _ => firstName + " " + lastName
  }

  def withAlias(alias: String) = new Artist(firstName, middleName, lastName, albums, Some(alias))

  override def hashCode = 123 * (123 + firstName.hashCode) + lastName.hashCode

  override def equals(other: Any) = other match {
    case that: Artist => this.firstName == that.firstName && this.lastName == that.lastName
    case _ => false
  }

  override def toString = "Artist[%s, %s]".format(firstName, lastName)
}

