## NOTAS:

* Para cambiar lo de probar tests de unidad sin haber implementado todo 
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> application -> airbdbserviceimpl -> before launch -> remove 'build' and add 'build no error check'
	3. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	


* Usar usuario __bd2_grupo2 para admin la db bd2_grupo2__, con password 'topsecret'.

* Cambiar todo respetando repository pattern

* Cuando creas una ciudad checkear no haya otra con igual nombre (case insensitive)

* Chequear hirarchy property apartment room

* Preguntar que onda si se hace rollback sobre crear user ponele y devuelvo el objeto usuario como si se guardo pero no ... debo encerrar la llamanda al saveUser(user) en un try catch y en el catch de ese metodo por si se levanta hibernateexception volver a tirarla?

* El tema de darme cuenta si no existe una ciudad al crear aprtment ... crearla etc todo dentro de una misma tx !!! super metodo.
