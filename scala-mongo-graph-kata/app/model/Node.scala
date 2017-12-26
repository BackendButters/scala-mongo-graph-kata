package model

import reactivemongo.bson.BSONObjectID

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class Node(name: String,
                connectsTo: mutable.ListBuffer[Node],
                override val id: BSONObjectID) extends MongoObjectIdentifier {

  def this(name: String) = this(name, new ListBuffer[Node], BSONObjectID.generate())

  def connectNode(node: Node): Unit = connectsTo += node

  def disconnectNode(node: Node): Option[Node] =
    connectsTo
      .find(_.equals(node))
      .map(n => {
        connectsTo -= n
        n
      })
}


