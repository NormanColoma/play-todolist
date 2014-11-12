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
		    	/*cat1.id muestEqual 1
		    	cat1.name must equalTo("Sports")
		    	cat1.t_user must equalTo("norman")
		    	cat2.id mustEqual 2
		    	cat2.name must equalTo("Videos")
		    	cat2.t_user must equalTo("norman")*/
		    }
		}
	}
}