import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import play.api.test._
import play.api.test.Helpers._

import models._

class ModelTaskF3 extends Specification {
   
  "Task model_feature3" should {
	    "setting and getting date for tasks" in {
		    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
		    	Task.create("First task")
		    	Task.create("Second task")
		    	val formatter:SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd")
		    	val date1:Date = formatter.parse("2014/10/05")
		    	val date2:Date = formatter.parse("2014/11/05")
		    	val result1 = Task.setDate(date1,1) //Task exists
		    	val result2 = Task.setDate(date2,2)	//Task exists
		    	val result3 = Task.setDate(date2,3)	//Task doesn't exists
		    	result1 mustEqual 1 
		    	result2 mustEqual 1
		    	result3 mustEqual 0

		    	val Some(fd1) = Task.getDate(1) //Task exists
		    	val Some(fd2) = Task.getDate(2)	//Task exists
		    	val formattedD3:Option[Date] = Task.getDate(4)	//Task doesn't exist
		    	val formattedD1 = formatter.format(fd1)
		    	val formattedD2 = formatter.format(fd2)
		    	formattedD1 must equalTo("2014/10/05")
		    	formattedD2 must equalTo("2014/11/05")
		    	formattedD3 must beNone
		    }
	    }
	}
}