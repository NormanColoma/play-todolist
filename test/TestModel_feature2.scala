import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import models._

class ModelTaskF2 extends Specification {
   
  "Task model" should {
	    "check if user exists" in {
		      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		      		//Users that exist
			      	val Some(user1) = Task.existUser("norman")
			      	val Some(user2) = Task.existUser("anonimo")
			      	val Some(user3) = Task.existUser("domingogallardo")
			      	user1 must equalTo("norman")
			      	user2 must equalTo("anonimo")
			      	user3 must equalTo("domingogallardo")

			      	//Users that doesn't exist
			      	val invalid_u1 = Task.existUser("pepe")
			      	val invalid_u2 = Task.existUser("juan")
			      	invalid_u1 must beNone 
			      	invalid_u2 must beNone

		      }
	    }

	    "creating and getting tasks from users" in {
		      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		      		//Users disntinc than default user(anonimo)
		      		Task.createWithUser("First task for Norman", "norman")
		      		Task.createWithUser("Second task for Norman", "norman")
		      		Task.createWithUser("First task for Domingo", "domingogallardo")
		      		Task.createWithUser("Second task for Domingo", "domingogallardo")
		      		val norman_list:List[Task] = Task.getTaskByName("norman")
		      		val domingo_list:List[Task] = Task.getTaskByName("domingogallardo")
		      		norman_list.length mustEqual 2
		      		norman_list(0).id mustEqual 1 
		      		norman_list(0).t_user must equalTo("norman")
			        norman_list(0).label must equalTo("First task for Norman")
			        norman_list(1).id mustEqual 2
			        norman_list(1).t_user must equalTo("norman")
			        norman_list(1).label must equalTo("Second task for Norman")
			        domingo_list.length mustEqual 2
		      		domingo_list(0).id mustEqual 3 
		      		domingo_list(0).t_user must equalTo("domingogallardo")
			        domingo_list(0).label must equalTo("First task for Domingo")
			        domingo_list(1).id mustEqual 4
			        domingo_list(1).t_user must equalTo("domingogallardo")
			        domingo_list(1).label must equalTo("Second task for Domingo")

			        //Created by anonymous user 
			        Task.create("First task for anonymous")
			        Task.create("Second task for anonymous")
			        val an_list:List[Task] = Task.getTaskByName("anonimo")
			        an_list.length mustEqual 2
		      		an_list(0).id mustEqual 5 
		      		an_list(0).t_user must equalTo("anonimo")
			        an_list(0).label must equalTo("First task for anonymous")
			        an_list(1).id mustEqual 6
			        an_list(1).t_user must equalTo("anonimo")
			        an_list(1).label must equalTo("Second task for anonymous")
		      }
	    }
   }
}
