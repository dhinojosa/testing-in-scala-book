package com.oreilly.testingscala

class CompilationAlbum(override val title: String,
                       override val year: Int,
                       val tracksAndActs: (Track, List[Act])*)
  extends Album(title, year,
                Some(tracksAndActs.map(_._1).toList),
                tracksAndActs.flatMap(_._2).distinct:_*)