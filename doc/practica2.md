Lo primero a comentar, será la implementación de la nueva parte de la práctica, es decir, las categorías: 

Hemos creado el  modelo **(Task)** el cual lo hemos implementado de
la siguiente forma: 


**case class Category**(id: **Long**, name: **String**, user: **String**)

Como se puede observar, nuestro modelo Category, está compuesto por diversos atributos: 

* **id:** Para identificar nuestra categoría mediante la id.
* **name:** Se usará en los formularios y servirá para darle nombre a la categoría. 
* **user:** Nombre del usuario el cual ha creado a la categoría. 

Una vez comentada la decisión de dicha implementación, vamos a comentar las funcionalidades implementadas en esta nueva versíon de la práctica de nuestro controller: 

* **newCategory:** Responde a la petición "POST /:user/category" devolviendo un objeto JSON con el nombre de la categoría creado y el estado HTTP "CREATED".
* **addTask** Responde a la petición "POST /:user/category/:cat/:id" devolviendo un objeto JSON con el mensaje de creación satisfactorio y el estado HTTP "OK". 
* **changeTask:** Responde a la petición "POST /:user/category/:cat/modify/:id" devolviendo el estado HTTP "Ok". Actualiza la descripción de la tarea de dicha categoría. Se recibe por parámetro mediante el label. Obviamos esta última parte, ya que suponemos que está implementada una vista para que el usuario pueda cambiar la descripción.
* **getTaskBYCategory:** Responde a la petición  "GET /:user/:id/tasks"  devolviendo el estado HTTP "OK". Devuelve una lista JSON con todas las tareas de esa categoría de ese usuario. 
* **getCategoriesByUser:** Responde a la petición "GET /:user/categories" devolviendo el estado HTTP"OK". Devuelte una lista JSON con todas las cateogrías creadas por dicho usuario.


Por último, cabe comentar, que para realizar los test hemos empleado TDD.