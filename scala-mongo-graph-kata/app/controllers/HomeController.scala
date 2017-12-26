package controllers

import javax.inject._

import play.api._
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.mvc.{BaseController, _}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.play.json._


@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val reactiveMongoApi: ReactiveMongoApi,
                              val parsers: PlayBodyParsers)
  extends BaseController with MongoController with ReactiveMongoComponents {

  override lazy val parse: PlayBodyParsers = parsers

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getById(id: BSONObjectID) = Action.async {

    val nodesCollection = getCollection("Nodes")

    val cursor: Future[Cursor[JsObject]] = nodesCollection.map {
      _.find(Json.obj("name" -> "as")).
        // sort them by creation date
        sort(Json.obj("created" -> -1)).
        cursor[JsObject]()
    }

    // gather all the JsObjects in a list
    val futurePersonsList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List]())

    // transform the list into a JsArray
    val futurePersonsJsonArray: Future[JsArray] =
      futurePersonsList.map { persons => Json.arr(persons) }

    // everything's ok! Let's reply with the array
    futurePersonsJsonArray.map { persons =>
      Ok(persons)
    }
  }

  def asd = Action.async {
    Future {
      // Call some blocking API
      Ok("result of blocking call")
    }
  }

  def getCollection(name: String): Future[JSONCollection] =
    database.map(_.collection[JSONCollection](name))
}
