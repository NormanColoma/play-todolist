import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import models._

class ModelTaskF4 extends Specification {
   
  "Task model_feature4" should {
	    "creating categories for users" in {
		    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		    	val cat1= Category.newCategory("Sports","norman") //User that exists
		    	val cat2= Category.newCategory("Videos","norman") //User that exists
		    	cat1 mustEqual 1 
		    	cat2 mustEqual 1
		    }
		}
		"getting categories of users" in {
		    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		    	Category.newCategory("Sports","norman") //User that exists
		    	Category.newCategory("Videos","norman") //User that exists
		    	val categories = Category.getCategories("norman")
		    	categories.length mustEqual 2 
		    	categories(0).id mustEqual 1
		    	categories(0).name must equalTo("Sports")
		    	categories(1).name must equalTo("Videos")
		    	categories(1).id mustEqual 2
		    }
		}

		"adding tasks to category" in{
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
				Category.newCategory("Sports","norman")
		    	Task.createWithUser("Football", "norman")
		      	Task.createWithUser("Basketball", "norman")
		    	val result1 = Category.addTask(1,1)
		    	val result2 = Category.addTask(2,1)
		    	result1 mustEqual 1 
		    	result2 mustEqual 1


		    }
		}

		"adding same task to different categories" in{
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

				Category.newCategory("Videos","norman")
				Category.newCategory("Sports","norman")
				Task.createWithUser("Football", "norman")
				var sameTask1 = Category.addTask(1,1)
				var sameTask2 = Category.addTask(1,2)
				sameTask1 mustEqual 1 
				sameTask2 mustEqual 1

		    }
		}

		"modifying task of category" in{
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
				Category.newCategory("Sports","norman")
		    	Task.createWithUser("Football", "norman")
		    	var result1 = Category.modify(1,"Baskteball")
		    	result1 mustEqual 1

		    }
		}

		"getting id of Category" in{
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
				Category.newCategory("Sports","norman")
		    	var Some(result1) = Category.getID("Sports","norman")
		    	result1 mustEqual 1
		    	var result2:Option[Long] = Category.getID("Basketball","norman")
		    	resutl2 must beNone

		    }
		}
	}
}