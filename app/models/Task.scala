package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Task(id: Long, label: String)



object Task {

   val task = {
      get[Long]("id") ~ 
      get[String]("label") map {
         case id~label => Task(id, label)
      }
   }

   def all(): List[Task] = DB.withConnection { implicit c =>
     SQL("select * from task").as(task *)
   }

   def getTask(id: Long): Task = DB.withConnection { implicit c =>
     SQL("select * from task where id = {id}").on('id -> id).as(task.single)
   }

   def create(label: String) {
     DB.withConnection { implicit c =>
       SQL("insert into task (label) values ({label})").on(
         'label -> label
       ).executeUpdate()
     }
   }  
   
   def delete(id: Long): Int = {
     val result: Int = DB.withConnection { implicit c =>
       SQL("delete from task where id = {id}").on(
         'id -> id
       ).executeUpdate()
     }
     result
   }
}