package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Category(id: Long, name: String, c_user:String)



object Category {
	val category = {
      get[Long]("id") ~ 
      get[String]("name") ~
      get[String]("c_user") map {
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

   def newCategory(name: String, c_user:String):Int = {
     val result: Int= DB.withConnection { implicit c =>
       SQL("insert into category (name,c_user) values ({name},{c_user})").on(
         'name -> name, 'c_user -> c_user
       ).executeUpdate()
     }
     result
   }

   def addTask(id_task: Long, id_category:Long, tcat_user:String):Int = {
     val result: Int= DB.withConnection { implicit c =>
       SQL("insert into tcat (id_task,id_category,tcat_user) values ({id_task},{id_category},{tcat_user})").on(
         'id_task -> id_task, 'id_category -> id_category, 'tcat_user -> tcat_user
       ).executeUpdate()
     }
     result
   }

   def getCategories(c_user:String):List[Category] =  DB.withConnection { implicit c =>
   	SQL("select * from category where c_user = {c_user}").on('c_user -> c_user).as(category *)
   }


   def getCategory(id:Long):Option[Category] =  DB.withConnection { implicit c =>
   	SQL("select * from category where id = {id}").on('id -> id).as(category.singleOpt)
   }



   def getID(name:String, c_user:String): Option[Long] = DB.withConnection{ implicit c => 
      SQL("select id from category where name = {name} and c_user = {c_user}").on('name -> name, 'c_user -> c_user).as(scalar[Long].singleOpt)
   }


   def modify(id_task:Long, label: String):Int = {
     val result: Int= DB.withConnection { implicit c =>
        SQL("update task set label = {label} where id = {id_task}").on(
         'id_task-> id_task, 'label -> label
       ).executeUpdate()
     }
     result
   }

   def getTasks(id_category:Long, tcat_user:String):List[Task] = DB.withConnection{ implicit c => 
   	SQL("select * from task inner join tcat on id_task = id where id_category = {id_category} and tcat_user ={tcat_user}").on(
   		'id_category -> id_category, 'tcat_user -> tcat_user).as(task *)
   }

}