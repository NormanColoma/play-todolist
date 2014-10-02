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
  (JsPath \ "label").write[String] and
  (JsPath \ "t_user").write[String]
)(unlift(Task.unapply))

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
    
    val json= Task.getTask(id)
    if(json == None)
      NotFound("Task has not been found")
    else
      Ok(Json.toJson(json))
  }

  def getTaskByUser(user: String) = Action{
    try{
      if(Task.existUser(user) == user){
        Ok(Json.toJson(Task.getTaskByName(user)))
      }
      else

        NotFound("User "+user+" doesn't exist")
    }catch{
      case e: Exception => NotFound("User "+user+" doesn't exist")
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

  def newTaskUser(user: String) = Action {
    implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        try{
          Task.createWithUser(label,user)
          Created((Json.toJson("Task: "+label+". Created by: "+user)))
        }
        catch{
          case e: Exception => NotFound("User "+user+" doesn't exist")
        }
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