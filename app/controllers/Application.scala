package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Task
import play.api.libs.json._
import play.api.libs.functional.syntax._


object Application extends Controller {

  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "label").write[String]
  )(unlift(Task.unapply))

  implicit val taskReads: Reads[Task] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "label").read[String]
  )(Task.apply _)

  def index = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def tasks = Action {
     Ok(Json.toJson(Task.all()))
  }

  def getTask(id: Long) = Action{
   
   try {
    Ok(Json.toJson(Task.getTask(id)))
   }catch{
    case e: Exception => NotFound("The task has not been found")
   }
    
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Created((Json.toJson(label)))
      }
    )
  }

  def deleteTask(id: Long) = Action {
    if(Task.delete(id) > 0)
      Ok("Task has been deleted")
    else 
      NotFound("Task has not been found")
  } 

}