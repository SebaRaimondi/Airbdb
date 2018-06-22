## NOTAS:

* Para cambiar lo de **probar tests de unidad** sin haber implementado todo (IntelliJ Idea)
	--> https://stackoverflow.com/questions/13031595/run-unit-tests-in-intellij-with-errors-in-classes 
	1. file -> settings -> Build, E, Deploy -> compiler -> java compiler -> change javac to eclipse -> check 'Proceed on errors'
	2. run -> edit configurations -> JUnit -> airbdbservicetestcase -> before launch -> remove 'build' and add 'build no error check'	

* Usar __usuario bd2_grupo2 para admin la db bd2_grupo2, con password 'topsecret'__.

### Considerar para codear ...
* session.getCurrentSession para no parametrizar la session
* anotacion transactional en los metodos de la implementacion q implican algo atomico para no hacer tx commit etc en el repo
* que las excepciones las levante el servicio (ese sería el que avisa oops algo anda mal)
* que el repo solo acceda a datos
* usar Query en vez de TypedQuery, ver como referencia el método isPropertyAvailable en el repo de acceso a datos
* NO usar Criteria para las consultas
---

## TAREAS:

* Arreglar el test Collision que pasa solo pero no cuando se ejecutan todos juntos.
* Hacer TPE2
* Hacer TP2
* Hacer TP2E2

---

## DUDAS:






