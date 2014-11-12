import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import play.api.libs.functional.syntax._


import models._

class ApplicationTaskF1 extends Specification {
	
	"Application" should {

		"creating new category" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		  		val result = controllers.Application.newCategory(FakeRequest(POST, "/norman/category").withFormUrlEncodedBody("label" -> "Football"))
		        status(result) must equalTo(CREATED)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("Category Football was created successfully")
		  	}
		}
	}
}