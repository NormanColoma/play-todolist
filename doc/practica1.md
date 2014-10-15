En primer lugar vamos a hablar sobre la implementación empleada: 

Nos decidimos por solo tener un modelo **(Task)** el cual lo hemos implementado de
la siguiente forma: 


**case class Task**(id: **Long**, label: **String**, t_user: **String**, t_date: **Option[Date]**)

Como se puede observar, nuestro modelo Task, está compuesto por diversos atributos: 

* **id:** Para identificar nuestra tarea mediante la id. 
* **label:** Se usará en los formularios y servirá para la descripción y fehca de la tarea. 
* **t_user:** Nombre del usuario el cual ha creado la tarea (por defecto anónimo). 
* **t_date:** Representa la fecha de finalización de la tarea. 

Una vez comentada la decisión de dicha implementación, vamos a comentar las funcionalidades de nuestro controller: 

* **tasks:** Devuelve una lista JSON de las tareas que pertenecen al usuario anónimo. 
* **getDate:** Responde a la petición "GET /tasks/:id/date" devolviendo un objeto JSON con la fecha de la tarea indicada mediante el atributo id. 
* **setEndDate:** Responde a la petición "POST /tasks/:id/date" devolviendo el estado HTTP "Ok". Actualiza la fecha de finalización de la tarea indicada mediante el atributo id. Recibe la fecha mediante un formulario en nuestra label. Esta última parte la obviamos, pues suponemos que hay una vista implementada en el eque el usuario pueda cambiar la fecha de la tarea introduciéndola en el formulario. 
* **getTask:** Responde a la petición "GET /tasks/:id" devolviendo un objeto JSON con la tarea indicada por parámetro, en caso de que esta exista. 
* **getTaskByUser:** Responde a la petición "GET /:user/tasks" devolviendo una lista con objetos JSON de todas las tareas creadas por dicho usuario. 
* **newTask:** Responde a la petición "POST	/tasks" recoge el label (descripción de la tarea) del formulario, y la crea. Responde con el estado HTTP "Created" y devuelve un objeto JSON con la descripción de la tarea cread. 
* **newTaskUser:** Lo mismo que el action anterior, solo que ahora el usuario especificado es el que crea la tarea. 
* **deleteTask:** Responde a la petición "DELETE /tasks/:id". Borra la tarea indicada por la id (en caso de que esta exista) y responde con el estado HTTP "Ok". 
