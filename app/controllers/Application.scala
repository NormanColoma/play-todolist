package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Task
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat


object Application extends Controller {


  val formatter:SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd")

  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "label").write[String] and 
    (JsPath \ "t_user").write[String] and 
    (JsPath \ "t_date").write[String].contramap[Option[Date]](dt => 
    if(dt == None)
      "None"
    else
      formatter.format(dt.getOrElse(""))
    )
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

  def getDate(id:Long) = Action{
    val task= Task.getTask(id)
    if(task != None){
      val date = Task.getDate(id)
      if(date != None)
        Ok(Json.toJson(formatter.format(date.getOrElse(""))))
      else
        NotFound("Task doesn't have date yet")
    }
    else
      NotFound("Task has not been found")
  }

  def setEndDate(id:Long) = Action{ implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label=> {
        val date: Date = formatter.parse(label)
        if(Task.setDate(date, id) > 0)
          Ok("Date has been updated")
        else 
          NotFound("Task has not been")
      }
    )
  }


  def getTask(id: Long) = Action{
    
    val json= Task.getTask(id)
    if(json == None)
      NotFound("Task has not been found")
    else
      Ok(Json.toJson(json))
  }

  def getTaskByUser(user: String) = Action{
    val t_user = Task.existUser(user)
    if(t_user != None){
      if(t_user.getOrElse(user) == user){
        val json = Task.getTaskByName(user)
        if(json.isEmpty)
          NotFound("User "+user+" doesn't have any task yet")
        else 
          Ok(Json.toJson(json))
      }
      else
        NotFound("User "+user+" doesn't exist")

    }
    else 
      NotFound("User "+user+" doesn't exist")
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
       val t_user = Task.existUser(user)
        if(t_user != None)
          Created((Json.toJson("Task: "+label+". Created by: "+user)))
        else
          NotFound("User "+user+" doesn't exist")
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