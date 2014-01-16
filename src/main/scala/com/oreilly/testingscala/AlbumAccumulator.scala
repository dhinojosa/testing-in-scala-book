package com.oreilly.testingscala

object AlbumAccumulator {
   def accumulate(map:Map[String, Int], tuples:Seq[(String, Int)]) = {
     tuples.foldLeft(map)((a,b) => a + b)
   }
}