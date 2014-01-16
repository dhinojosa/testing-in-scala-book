package com.oreilly.testingscala

import org.joda.time.format.PeriodFormatterBuilder

class Track(val name: String, val length: String) {

  require(name.trim().length() != 0, "Track name cannot be blank")

  def this(name: String) = this(name, "0:00")

  def period = {
    val fmt = new PeriodFormatterBuilder()
      .printZeroAlways()
      .appendMinutes()
      .appendSeparator(":")
      .printZeroAlways()
      .appendSeconds()
      .toFormatter
    fmt.parsePeriod(length)
  }
}