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
    Redirect(routes.Application.tasks)
  }

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def tasks = Action {
     Ok(views.html.index(Task.all(), taskForm))
  }

  def getTask(id: Long) = Action{
   
   try {
    Ok(Json.toJson(Task.getTask(id)))
   }catch{
    case e: Exception => NotFound("The task have not been found")
   }
    
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Ok(Json.toJson(label))
      }
    )
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  } 

}