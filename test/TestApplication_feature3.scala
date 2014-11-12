import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import play.api.libs.functional.syntax._


import models._

class ApplicationTaskF3 extends Specification {
	
	"Application feature3" should {

		"posting date for task that exists" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
				controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
				controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Second Task"))
				var d1 = controllers.Application.setEndDate(1)(FakeRequest(POST, "/tasks/1/date").withFormUrlEncodedBody("label" -> "2014/10/16"))
				var d2 = controllers.Application.setEndDate(2)(FakeRequest(POST, "/tasks/2/date").withFormUrlEncodedBody("label" -> "2014/12/16"))
				status(d1) must equalTo(OK)
		        contentType(d1) must beSome("text/plain")
		       	contentAsString(d1) must contain("Date has been updated")
		       	status(d2) must equalTo(OK)
		        contentType(d2) must beSome("text/plain")
		       	contentAsString(d2) must contain("Date has been updated")
			}
		}
		"posting date for task that doesn't exist" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {	
				var d1 = controllers.Application.setEndDate(1)(FakeRequest(POST, "/tasks/1/date").withFormUrlEncodedBody("label" -> "2014/10/16"))
				var d2 = controllers.Application.setEndDate(2)(FakeRequest(POST, "/tasks/2/date").withFormUrlEncodedBody("label" -> "2014/12/16"))
				status(d1) must equalTo(NOT_FOUND)
		        contentType(d1) must beSome("text/plain")
		       	contentAsString(d1) must contain("Task has not been found")
		       	status(d2) must equalTo(NOT_FOUND)
		        contentType(d2) must beSome("text/plain")
		       	contentAsString(d2) must contain("Task has not been found")
			}
		}

		"getting date for task that exist" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {	
				controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
				controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Second Task"))
				//Tasks that already have date
				controllers.Application.setEndDate(1)(FakeRequest(POST, "/tasks/1/date").withFormUrlEncodedBody("label" -> "2014/10/16"))
				controllers.Application.setEndDate(2)(FakeRequest(POST, "/tasks/2/date").withFormUrlEncodedBody("label" -> "2014/12/16"))
				var d1 = controllers.Application.getDate(1)(FakeRequest(GET, "/tasks/1/date"))
				var d2 = controllers.Application.getDate(2)(FakeRequest(GET, "/tasks/2/date"))
				status(d1) must equalTo(OK)
		        contentType(d1) must beSome("application/json")
		       	contentAsString(d1) must contain("2014/10/16")
		       	status(d2) must equalTo(OK)
		        contentType(d2) must beSome("application/json")
		       	contentAsString(d2) must contain("2014/12/16")

		       	//Tasks that don't have date yet
		       	controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Third Task"))
		       	var d3 = controllers.Application.getDate(3)(FakeRequest(GET, "/tasks/3/date"))
		       	status(d3) must equalTo(NOT_FOUND)
		        contentType(d3) must beSome("text/plain")
		       	contentAsString(d3) must contain("Task doesn't have date yet")

			}
		}
	}
}