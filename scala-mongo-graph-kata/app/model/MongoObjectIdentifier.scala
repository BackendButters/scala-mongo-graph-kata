package model

import reactivemongo.bson.BSONObjectID

trait MongoObjectIdentifier extends Identifyable[BSONObjectID]{

  val id: BSONObjectID

  override def getId(): BSONObjectID = id
}
