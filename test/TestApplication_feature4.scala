import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import play.api.libs.functional.syntax._


import models._

class ApplicationTaskF4 extends Specification {
	
	"Application" should {

		"creating new category" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		  		val result = controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Football"))
		        status(result) must equalTo(CREATED)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("Category Football was created successfully")
		  	}
		}

		"getting categories of user" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		  		//User that exits and he already created categories
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Football"))
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Basketball"))
		  		val result = controllers.Application.getCategoriesByUser("norman")(FakeRequest(GET, "/norman/categories"))
		        status(result) must equalTo(OK)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("""[{"id":1,"name":"Football","user":"norman"},{"id":2,"name":"Basketball","user":"norman"}]""")

		       	//User that doesn't exist 
		       	val result1 = controllers.Application.getCategoriesByUser("pepe")(FakeRequest(GET, "/pepe/categories"))
		        status(result1) must equalTo(NOT_FOUND)
		        contentType(result1) must beSome("text/plain")
		       	contentAsString(result1) must contain("User pepe doesn't exist")

		       	//User that exist but he has not created categories yet
		       	val result2 = controllers.Application.getCategoriesByUser("domingogallardo")(FakeRequest(GET, "/domingogallardo/categories"))
		        status(result2) must equalTo(NOT_FOUND)
		        contentType(result2) must beSome("text/plain")
		       	contentAsString(result2) must contain("User domingogallardo doesn't have any category yet")
		  	}
		}

		"users adding tasks to some category" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Sports"))
		  		controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Football"))
		  		controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Basket"))
		  		val result = controllers.Application.addTask(1,1,"norman")(FakeRequest(POST, "/norman/category/1/1"))
		  		status(result) must equalTo(CREATED)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("Task was added successfully")
		
		  	}
		}

		"users modifying task of some category" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

		  		//User that exists+category that exists+task that exists
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Sports"))
		  		controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Football"))
		  		controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Basket"))
		  		controllers.Application.addTask(1,1,"norman")(FakeRequest(POST, "/norman/category/1/1"))
		  		var result = controllers.Application.changeTask(1,1,"norman")(FakeRequest(POST, "/norman/category/1/modify/1").withFormUrlEncodedBody("label" -> "Tennis"))
				status(result) must equalTo(CREATED)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("Category was changed successfully")	
		  	}
		}


		"users modifying task(that doesn't exist) of some category" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Sports"))	    		  	
		  		var result1 = controllers.Application.changeTask(1,1,"norman")(FakeRequest(POST, "/norman/category/1/modify/1").withFormUrlEncodedBody("label" -> "Tennis"))
				status(result1) must equalTo(NOT_FOUND)
		        contentType(result1) must beSome("text/plain")
		       	contentAsString(result1) must contain("Task has not been found")
		  	}
		}


		"users modifying task of some category(that doesn't exist)" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {		  		
		  		var result1 = controllers.Application.changeTask(1,1,"norman")(FakeRequest(POST, "/norman/category/1/modify/1").withFormUrlEncodedBody("label" -> "Tennis"))
				status(result1) must equalTo(NOT_FOUND)
		        contentType(result1) must beSome("text/plain")
		       	contentAsString(result1) must contain("Category has not been found")
		  	}
		}

		"getting tasks from some category of user" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {		  		
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Sports"))
		  		controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Football"))
		  		controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Basket"))
		  		controllers.Application.addTask(1,1,"norman")(FakeRequest(POST, "/norman/category/1/1"))
		  		controllers.Application.addTask(2,1,"norman")(FakeRequest(POST, "/norman/category/1/1"))
		  		var result = controllers.Application.newTaskUser("norman")(FakeRequest(GET, "/norman/1/tasks"))
		  		status(result) must equalTo(OK)
		        contentType(result) must beSome("application/json")
		  		contentAsString(result) must contain("""[{"id":1,"label":"Football","t_user":"norman","""
          +""""t_date":"None"},{"id":2,"label":"Basket","t_user":"norman","""
          +""""t_date":"None"}]""")

		  	}
		}


	}
}