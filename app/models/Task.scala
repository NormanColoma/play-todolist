package models
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String)

object Task {
  
  def all(): List[Task] = Nil
  
  def create(label: String) {}
  
  def delete(id: Long) {}

  def all(): List[Task] = DB.withConnection { implicit c =>
  	SQL("select * from task").as(task *)
  }

  val task = {
	  get[Long]("id") ~ 
	  get[String]("label") map {
	    case id~label => Task(id, label)
	  }
  }	
}