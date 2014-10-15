En primer lugar vamos a hablar sobre la implementación empleada: 

Nos decidimos por solo tener un modelo **(Task)** el cual lo hemos implementado de
la siguiente forma: 


<strong>case class Task</strong>(id: **Long**, label: **String**, t_user: **String**, t_date: **Option[Date]**)
