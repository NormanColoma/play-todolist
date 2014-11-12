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
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Football"))
		  		controllers.Application.newCategory("norman")(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Basketball"))
		  		val result = controllers.Application.getCategoriesByUser("norman")(FakeRequest(GET, "/norman/categories"))
		        status(result) must equalTo(OK)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("""[{"id":1,"name":"Football","user":"norman"},{"id":2,"name":"Basketball","user":"norman"}]""")
		  	}
		}
	}
}