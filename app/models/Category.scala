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

   def newCategory(name: String, user:String):Int = {
     val result: Int= DB.withConnection { implicit c =>
       SQL("insert into category (name,user) values ({name},{user})").on(
         'name -> name, 'user -> user
       ).executeUpdate()
     }
     result
   }

   def getCategories(user:String):List[Category] =  DB.withConnection { implicit c =>
   	SQL("select * from category where user = {user}").on('user -> user).as(category *)
   }
}