import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import models._

class ModelTaskF1 extends Specification {
   
  "Task model" should {
    "retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Task.create("First task")
        Task.create("Second task")

        val Some(f_task1) = Task.getTask(1);
        val Some(f_task2) = Task.getTask(2);
        f_task1.id mustEqual 1
        f_task1.label must equalTo("First task")
        f_task2.id mustEqual 2
        f_task2.label must equalTo("Second task")

        val f_task3:Option[Task] = Task.getTask(3);
        f_task3 must beNone

      }
    }
    "getting all tasks" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val EmptyTask_list:List[Task] = Task.all()
        EmptyTask_list.length mustEqual 0
        Task.create("First task on list")
        Task.create("Second task on list")
        val task_list:List[Task] = Task.all()
        task_list.length mustEqual 2
        task_list(0).id mustEqual 1 
        task_list(0).label must equalTo("First task on list")
        task_list(1).id mustEqual 2 
        task_list(1).label must equalTo("Second task on list")
      }
    }
    "deleting tasks" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Task.create("First task to delete")
        Task.create("Second task to delete")
        val delete1:Int = Task.delete(1)
        val delete2:Int = Task.delete(2)
        val delete3:Int = Task.delete(3)

        delete1 mustEqual 1 
        delete2 mustEqual 1
        delete3 mustEqual 0
      }
    }
  
  }

}