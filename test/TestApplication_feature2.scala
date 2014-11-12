import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import play.api.libs.functional.syntax._


import models._

class ApplicationTaskF2 extends Specification {
	
	"Application" should {

		"posting and getting task for users that exist" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		        val result1 = controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        val result2 = controllers.Application.newTaskUser("norman")(FakeRequest(POST, "/norman/tasks").withFormUrlEncodedBody("label" -> "Second Task"))
		        status(result1) must equalTo(CREATED)
		        contentType(result1) must beSome("application/json")
		       	contentAsString(result1) must contain("Task: First Task. Created by: norman")
		       	status(result2) must equalTo(CREATED)
		        contentType(result2) must beSome("application/json")
		       	contentAsString(result2) must contain("Task: Second Task. Created by: norman")		  
		       	val result3 = controllers.Application.newTaskUser("domingogallardo")(FakeRequest(POST, "/domingogallardo/tasks").withFormUrlEncodedBody("label" -> "Task for Domingo"))
		        status(result3) must equalTo(CREATED)
		        contentType(result3) must beSome("application/json")
		       	contentAsString(result3) must contain("Task: Task for Domingo. Created by: domingogallardo")
		       	val l_norman = controllers.Application.getTaskByUser("norman")(FakeRequest())
		       	status(l_norman) must equalTo(OK)
		        contentType(l_norman) must beSome("application/json")
		       	contentAsString(l_norman) must contain("""[{"id":1,"label":"First Task","t_user":"norman","""
          +""""t_date":"None"},{"id":2,"label":"Second Task","t_user":"norman","""
          +""""t_date":"None"}]""")


		       
			}
		}

		"posting and getting task for user that doesn't exist" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		        val result1 = controllers.Application.newTaskUser("pepe")(FakeRequest(POST, "/pepe/tasks").withFormUrlEncodedBody("label" -> "First Task"))
		        status(result1) must equalTo(NOT_FOUND)
		        contentType(result1) must beSome("text/plain")
		       	contentAsString(result1) must contain("User pepe doesn't exist")
		       	val result2 = controllers.Application.getTaskByUser("pepe")(FakeRequest())
		       	status(result2) must equalTo(NOT_FOUND)
		        contentType(result2) must beSome("text/plain")
		       	contentAsString(result2) must contain("User pepe doesn't exist")
			}
		}
	}
}