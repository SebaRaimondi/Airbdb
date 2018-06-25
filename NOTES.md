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

## ACTIVIDADES:

* Arreglar el test Collision y isPropAvailable que pasan solos pero no cuando se ejecutan todos juntos.
* Hacer TPE2
* Hacer TP2
* Hacer TP2E2

## TAREAS:

* Camila hacer primera mitad tests StatisticsService
* Camila hacer primera mitad pr mongo teorica
* Camila hacer primera mitad pr mongo práctica

---

## DUDAS:

* NO puedo hacer pasar el testRentPropertyCpllision cuando se ejecutan todos juntos (NOTAR AHORA TAMBIEN PASA ESO CON EL TESTISPROPERTYAVAILABLE!). Algo para remarcar es que si cambio el orden en que se crean las reservas de prueba, tampoco pasa individual. Es como que veo en el sql que se agregan las 2 reservas de prueba sin problemas, pero solamente se muestra el 'no podes agregar la nueva' cuando la nueva colisiona con la primer reserva agregada .... no entiendo. Si se quiere ver el metodo isPropertyAvailable en el repository, contempla el caso en el que la nueva reserva tenga una reserva vieja 'anidada' en sus fechas de from-to o bien sea ésta la que este entre las fechas from-to de una vieja.
 



