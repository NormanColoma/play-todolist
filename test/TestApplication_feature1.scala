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

		"posting new task" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		        val result = controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        status(result) must equalTo(CREATED)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("First Task")
			}
		}
		"posting new task error" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		        val result = controllers.Application.newTask(FakeRequest(POST, "/tasks"))
		        status(result) must equalTo(BAD_REQUEST)
			}
		}

		"getting all the tasks" in {
		  
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		        controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Second Task"))
		        val result = controllers.Application.tasks(FakeRequest())
		        status(result) must equalTo(OK)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("""[{"id":1,"label":"First Task","t_user":"anonimo","""
          +""""t_date":"None"},{"id":2,"label":"Second Task","t_user":"anonimo","""
          +""""t_date":"None"}]""")
			}
		}  

		"getting task that exists by id" in {
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		        controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        val result = controllers.Application.tasks(FakeRequest(GET, "/tasks/1"))
		        status(result) must equalTo(OK)
		        contentType(result) must beSome("application/json")
		       	contentAsString(result) must contain("""[{"id":1,"label":"First Task","t_user":"anonimo","""
          +""""t_date":"None"}]""")
			}
		}
		"getting task that doesn't exist by id" in {
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {		        
		        val result = controllers.Application.getTask(1)(FakeRequest())
		        status(result) must equalTo(NOT_FOUND)
		        contentType(result) must beSome("text/plain")
		       	contentAsString(result) must contain("Task has not been found")
		       	controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Second Task"))
		        val result2 = controllers.Application.getTask(3)(FakeRequest())
		        status(result2) must equalTo(NOT_FOUND)
		        contentType(result2) must beSome("text/plain")
		       	contentAsString(result2) must contain("Task has not been found")
			}
		}

		"deleting task that exist" in {
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {	
		  		controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Second Task"))	        
		        val result = controllers.Application.deleteTask(1)(FakeRequest())
		        val result2 = controllers.Application.deleteTask(2)(FakeRequest())
		        status(result) must equalTo(OK)
		        contentType(result) must beSome("text/plain")
		       	contentAsString(result) must contain("Task has been deleted")
		       	status(result2) must equalTo(OK)
		        contentType(result2) must beSome("text/plain")
		       	contentAsString(result2) must contain("Task has been deleted")
			}
		}

		"deleting task that doesn't exist" in {
		  	running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {			  		
		        val result = controllers.Application.deleteTask(1)(FakeRequest())
		        status(result) must equalTo(NOT_FOUND)
		        contentType(result) must beSome("text/plain")
		       	contentAsString(result) must contain("Task has not been found")
		       	controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        controllers.Application.newTask(FakeRequest(POST, "/tasks").withFormUrlEncodedBody("label" -> "Second Task"))	        
		        val result2 = controllers.Application.deleteTask(3)(FakeRequest())   
		       	status(result2) must equalTo(NOT_FOUND)
		        contentType(result2) must beSome("text/plain")
		       	contentAsString(result2) must contain("Task has not been found")
			}
		}
	}
}
