## NOTAS:

* Para cambiar lo de **probar tests de unidad** sin haber implementado todo (IntelliJ Idea)
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	

* Usar __usuario bd2_grupo2 para admin la db bd2_grupo2, con password 'topsecret'__.

### Consideraciones al codear ...

* Generales
	* que las excepciones las levante el servicio (ese sería el que avisa oops algo anda mal)
	* que el repo solo acceda a datos

* Practica 1
	* usar Query en vez de TypedQuery ... (se vr el cambio en la oparte 2, hay consultas que devuelven atts o conjunto de atts
	en vez de entidades, por eso te conviene no tipar el result)
	* NO usar Criteria para las consultas de la pr 1 (SQL), brinda menos flexibilidad.
	* session.getCurrentSession para no parametrizar la session
	* anotacion transactional en los metodos de la implementacion q implican algo atomico para no hacer tx commit etc en el
	repo
---



