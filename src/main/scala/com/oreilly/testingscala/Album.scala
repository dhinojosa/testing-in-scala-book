package com.oreilly.testingscala

import org.joda.time.Period

class Album (val title: String, val year: Int, val tracks: Option[List[Track]], val acts: Act*) {
  require(acts.size > 0)

  def this(title: String, year: Int, acts: Act*) = this(title, year, None, acts:_*)

  def ageFrom(now: Int) = now - year

  def period = tracks.getOrElse(Nil).map(_.period).foldLeft(Period.ZERO)(_.plus(_))

  override def toString = ("Album" + "[" + title + "]")
}
