## NOTAS:

* Para cambiar lo de **probar tests de unidad** sin haber implementado todo 
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> application -> airbdbserviceimpl -> before launch -> remove 'build' and add 'build no error check'
	3. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	

* Usar __usuario bd2_grupo2 para admin la db bd2_grupo2, con password 'topsecret'__.

## TAREAS:

* Pasar tests 6 y 7 y hacer script bash para inicializar la db.
* session.getCurrentSession para no parametrizar la session
* anotacion transactional en los metodos de la implementacion q implican algo atomico para no hacer tx commit etc en el repo
* que las excepciones las levante el servicio
* que el repo solo acceda a datos

## DUDAS:

* Check se respeta repository pattern

	-> Check caso 'is property available' modularizado debo pasarle session ... que onda cuando se pide impl en airdbserviceimpl
	-> ver ejemplo de managecity findcity y create, que pasa si se quieren hacer estos servicios en airbdbimpl? tengo que hacer otro metodo igual sin el param session? .... no esta tan buena la modularizacion.

* Check restricciones del modelo del tipo ... 'Cuando creas una ciudad checkear no haya otra con igual nombre (case insensitive)'. CON EXCEPCIONES? ver como seria ... pasa en user y properties tmb

* Preguntar que onda si se hace rollback sobre crear user ponele y devuelvo el objeto usuario como si se guardo pero no ... debo encerrar la llamanda al saveUser(user) en un try catch y en el catch de ese metodo por si se levanta hibernateexception volver a tirarla?

* Que onda cambiar interfaz para q tire exception username city?????

* Check lo de devolver el user by name o null en el repo o pasarle la lista de resultados  al service y q el haga eso ???



