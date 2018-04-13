## NOTAS:

* Para cambiar lo de **probar tests de unidad** sin haber implementado todo 
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> application -> airbdbserviceimpl -> before launch -> remove 'build' and add 'build no error check'
	3. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	

* Usar __usuario bd2_grupo2 para admin la db bd2_grupo2, con password 'topsecret'__.

* session.getCurrentSession para no parametrizar la session
* anotacion transactional en los metodos de la implementacion q implican algo atomico para no hacer tx commit etc en el repo
* que las excepciones las levante el servicio
* que el repo solo acceda a datos

## TAREAS:

* Pasar tests 6 y 7 y hacer script bash para inicializar la db.

## DUDAS:

* Check restricciones del modelo del tipo ... 'Cuando creas una ciudad checkear no haya otra con igual nombre (case insensitive)'. CON EXCEPCIONES? ver como seria ... pasa en user y properties tmb

... Que onda cambiar interfaz para q tire exception username repetido, cityname repetido????? tengo que checkear apartment en una ciudad es unico nombre?




