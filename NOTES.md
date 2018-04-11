## NOTAS:

* Para cambiar lo de probar tests de unidad sin haber implementado todo 
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> application -> airbdbserviceimpl -> before launch -> remove 'build' and add 'build no error check'
	3. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	


* Usar usuario bd2-grupo2 (guion bajo) para admin la db bd2-grupo2 (guion bajo), con password 'topsecret'.

* Cambie @Autowired private SessionFactory sessionFactory en AirBdRepository a @Autowired **public** SessionFactory sessionFactory para acceder a la sessionFactory desde AirBdServiceImpl a traves de la variable de instancia _repository_ de la forma `this.repository.sessionFactory` y asi tener una unidad de trabajo para hacer x transaccion.

* Que onda, si creo un apartment en una ciudad, deberia checkear que esa ciudad exista y sino dar error o bien cargarla en ese momento?


