package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Category(id: Long, name: String, t_user:String)



object Category {
	val category = {
      get[Long]("id") ~ 
      get[String]("label") ~
      get[String]("t_user")
   }

   def newCategory(name: String, t_user:String):Int {
     val result: Int= DB.withConnection { implicit c =>
       SQL("insert into category (name,t_user) values ({name, t_user})").on(
         'name -> name, 't_user -> t_user
       ).executeUpdate()
     }
     result
   }
}