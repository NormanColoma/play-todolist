package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Task(id: Long, label: String, t_user: String, t_date: Option[String])



object Task {
   val task = {
      get[Long]("id") ~ 
      get[String]("label") ~
      get[String]("t_user") ~
      get[Option[String]]("t_date") map {
         case id~label~t_user~t_date => Task(id, label, t_user, t_date)
      }
   }

   def all(): List[Task] = DB.withConnection { implicit c =>
     SQL("select * from task where t_user ={t_user}").on('t_user->"anonimo").as(task *)
   }

   def getTask(id: Long): Option[Task] = DB.withConnection { implicit c =>
     SQL("select * from task where id = {id}").on('id -> id).as(task.singleOpt)
   }

   def getDate(id: Long): String = DB.withConnection { implicit c =>
     SQL("select t_date from task where id = {id}").on('id -> id).as(scalar[String].single)
   }


   def existUser(name:String): Option[String] = DB.withConnection{ implicit c => 
      SQL("select name from task_user where name = {name}").on('name -> name).as(scalar[String].singleOpt)
   }

   def getTaskByName(t_user: String): List[Task] = DB.withConnection { implicit c => 
      SQL("select id,label,t_user from task where t_user = {t_user}").on('t_user -> t_user).as(task *)
   }

   def setDate(t_date:String, id:Long): Int = {
    val result: Int = DB.withConnection { implicit c =>
       SQL("update task set t_date = {t_date} where id ={id}").on(
         'id -> id, 't_date -> t_date
       ).executeUpdate()
     }
     result
   }

   def create(label: String) {
     DB.withConnection { implicit c =>
       SQL("insert into task (label) values ({label})").on(
         'label -> label
       ).executeUpdate()
     }
   }  

   def createWithUser(label: String, t_user: String){ DB.withConnection { implicit c =>
       SQL("insert into task (label,t_user) values ({label},{t_user})").on(
         'label -> label, 't_user -> t_user
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