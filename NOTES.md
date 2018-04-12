## NOTAS:

* Para cambiar lo de **probar tests de unidad** sin haber implementado todo 
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> application -> airbdbserviceimpl -> before launch -> remove 'build' and add 'build no error check'
	3. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	

## TAREAS:

* Usar __usuario bd2_grupo2 para admin la db bd2_grupo2, con password 'topsecret'__.

* Check se respeta repository pattern

	-> Check caso 'is property available' modularizado debo pasarle session ... que onda cuando se pide impl en airdbserviceimpl

* Check restricciones del modelo del tipo ... 'Cuando creas una ciudad checkear no haya otra con igual nombre (case insensitive)'

* Preguntar que onda si se hace rollback sobre crear user ponele y devuelvo el objeto usuario como si se guardo pero no ... debo encerrar la llamanda al saveUser(user) en un try catch y en el catch de ese metodo por si se levanta hibernateexception volver a tirarla?

* Pasar tests 6 y 7 y hacer script bash para inicializar la db.

