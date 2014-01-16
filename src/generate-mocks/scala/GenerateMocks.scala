import com.oreilly.testingscala._
import org.scalamock.annotation.{mockWithCompanion, mockObject, mock}

@mock[Artist]
@mock[Album]
@mock[Track]
@mock[Band]
@mock[JukeBox]
@mockWithCompanion[DAO]
@mockObject(BruceSpringsteenFactory)
@mockWithCompanion[GrammyAlbumAward]
class GenerateMocks