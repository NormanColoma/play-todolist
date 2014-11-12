package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Category(id: Long, name: String, user:String)



object Category {
	val category = {
      get[Long]("id") ~ 
      get[String]("name") ~
      get[String]("user") map {
         case id~name~user=> Category(id, name, user)
      }
   }

    val task = {
      get[Long]("id") ~ 
      get[String]("label") ~
      get[String]("t_user") ~
      get[Option[Date]]("t_date") map {
         case id~label~t_user~t_date => Task(id, label, t_user, t_date)
      }
   }

   def newCategory(name: String, user:String):Int = {
     val result: Int= DB.withConnection { implicit c =>
       SQL("insert into category (name,user) values ({name},{user})").on(
         'name -> name, 'user -> user
       ).executeUpdate()
     }
     result
   }

   def addTask(id_task: Long, id_category:Long, user:String):Int = {
     val result: Int= DB.withConnection { implicit c =>
       SQL("insert into tcat (id_task,id_category,user) values ({id_task},{id_category},{user})").on(
         'id_task -> id_task, 'id_category -> id_category, 'user -> user
       ).executeUpdate()
     }
     result
   }

   def getCategories(user:String):List[Category] =  DB.withConnection { implicit c =>
   	SQL("select * from category where user = {user}").on('user -> user).as(category *)
   }

   def getID(name:String, user:String): Option[Long] = DB.withConnection{ implicit c => 
      SQL("select id from category where name = {name} and user = {user}").on('name -> name, 'user -> user).as(scalar[Long].singleOpt)
   }


   def modify(id_task:Long, label: String):Int = {
     val result: Int= DB.withConnection { implicit c =>
        SQL("update task set label = {label} where id = {id_task}").on(
         'id_task-> id_task, 'label -> label
       ).executeUpdate()
     }
     result
   }

   def getTasks(id_category:Long, user:String):List[Task] = DB.withConnection{ implicit c => 
   	SQL("select * from task inner join tcat on id_task = id where id_category = {id_category} and user ={user}").on(
   		'id_category -> id_category, 'user -> user).as(task *)
   }

}