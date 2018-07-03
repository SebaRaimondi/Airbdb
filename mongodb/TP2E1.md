Parte 1: Bases de Datos NoSQL Y Relacionales

▶︎ Si bien las BBDD NoSQL tienen diferencias fundamentales con los sistemas de BBDD
Relacionales o RDBMS, algunos conceptos comunes se pueden relacionar. Responda las
siguientes preguntas, considerando MongoDB en particular como Base de Datos NoSQL.

1) ¿Cuáles de los siguientes conceptos de RDBMS existen en MongoDB? En caso de no existir,
¿hay alguna alternativa? ¿Cuál es?
• Base de Datos
• Tabla / Relación
• Fila / Tupla
• Columna

En MongoDB, existe el concepto de *Base de Datos*, considerando presenta algunas diferencias con respecto al mismo en esquemas relacionales. Se explicará a continuación.

El término *Tabla* o *Relación* no existe. Hablamos más bien de *Colecciones*.

No hay *Filas* o *Tuplas*, sino *Documentos*.

No hablamos de *Columnas*; más bien hablamos de *Campos*.

MongoDB guarda la estructura de los datos de una base de datos particular en documentos tipo JSON con un esquema dinámico llamado BSON, lo que implica que no existe un esquema predefinido. 

Los elementos de los datos se denominan documentos y se guardan en colecciones. 

Una colección puede tener un número indeterminado de documentos. 

Mientras que en una base de datos relacional cada registro en una tabla tiene la misma cantidad de campos, en MongoDB cada documento en una colección puede tener diferentes campos. En un documento, se pueden agregar, eliminar, modificar o renombrar nuevos campos en cualquier momento,​ ya que no hay un esquema predefinido. 

La estructura de un documento es simple y está compuesta por pares clave/valor, debido a que como dijimos MongoDB sigue el formato de JSON. La clave es el nombre del campo y el valor es su contenido, los cuales se separan mediante el uso de “:”. Como valor se pueden usar números, cadenas o datos binarios como imágenes o cualquier otro.

2) MongoDB tiene soporte para transacciones, pero no es igual que el de los RDBMS. ¿Cuál es
el alcance de una transacción en MongoDB?

En MongoDB una operacion en un solo documento es atómica. Debido al hecho de que se pueden utilizar documentos y arreglos embebidos para representar las relaciones entre datos en un solo documento en vez de normalizar entre muchos documentos y colecciones, esta atomicidad dentro de un solo documento elimina la necesidad del uso de transacciones para muchos casos de uso. Es por ésto que hasta hace poco MongoDB no soportaba transacciones multidocumento.

Para permitir a los desarrolladores abarcar facilmente el espectro completo de casos de usos, con la version 4.0 de MongoDB, se agregaron las transacciones para respetar ACID en la actualización de múltiples colecciones las cuales pueden estar en diferentes bases de datos. A traves de _snapshot isolation_, las transacciones proveen una vista consistente de los datos y permiten una ejecución de "todo o nada" para mantener integridad. En MongoDB 4.0, las transacciones funcionan sólo para los  _replica sets_. Un _replica set_ es un grupo de procesos mongod que utilizan y mantienen los mismos grupos de datos. Así, se provee alta disponibilidad y balance de carga. 

El manejo en cuanto a sintaxis en codigo es muy similar al de transacciones en esquemas relacionales.

Se estima que próximamente se soportaran transacciones multidocumento también en _sharded clusters_ (refiere al hecho de tener los datos en diferentes servidores).

3) Para acelerar las consultas, MongoDB tiene soporte para índices. ¿Qué tipos de índices
soporta?

Un índice es una estructura de datos especial que guarda una pequeña porción de datos ordenados por el valor de algun campo según se especifique. Como bien se dijo, los índices permiten la resolución de queries de forma eficiente ya que sin ellos, MongoDB debería analizar todos los documentos de una coleccion para seleccionar aquellos que coincidan con la consulta (más aún si se busca por algún campo cuyo valor puede repetirse en distintos documentos).

Tipos de índices soportados:

* De un solo campo
    * Se crean de la forma `db.records.createIndex( { <field>: < 1 or -1 > } )`. Se puede optar por el valor 1, lo que implica ordenar los items en orden ascendente, o bien por el valor -1, indicando ordenar de forma descendente.

* De múltiples campos
    * Con éstos una sola estructura de indices mantiene referencias a múltiples campos de los documentos de una colección. Así, se soportan consultas que matchean múltiples campos. MongoDB impone un limite de 31 campos para todo indice compuesto. Forma de crearlos: `db.collection.createIndex( { <field1>: <type>, <field2>: <type2>, ... } )`. Notar debemos especificar 1 o -1. El orden de los campos es importante ya que determina por cual se ordena primero y por cual se ordena luego. No se puede poner tipo hash.
    
* Multiclave
    * Se utiliza para indexar el contenido de arreglos almacenados. Si indexas un campo que tiene como valor un arreglo, MongoDB crea a parte una entrada separada de indice para cada elemento del arreglo. Así permiten a las consultas seleccionar documentos que contienen un arreglo y coinciden en ciertos elementos. 

* Geoespacial
    * Soportan consultas eficientes acerca de datos de coordenadas geoespaciales. Hay dos índices especiales: índices 2d que usan geometria plana al retornar resultados, y los índices 2dsphere que usan geometria esféria para  devolver resultados.

* Texto
    * Se usa para soportar búsqueda por algún _string_ en una colección. Una colección puede tener como mucho un índice tipo texto. Ejemplo: `db.reviews.createIndex( { <field1>: "text" } )`, donde <field1> es un campo que tiene un sring o un array de strings. Hay muchas opciones que se pueden especificar, como por ejemplo case insensitive, wildcards, etc.

* Hasheado
    * Permite indexar el valor hasheado de un campo. Tienen una distribución más random de valores a lo largo del rango pero solo soportan consultas que checkean coincidencia de igualdad y no queries basadas en rangos.


4) ¿Existen claves foráneas en MongoDB?



------------------------------------------------
Parte 2

5) 
    Creo la base de datos con 'use airbdb'
    Ejecuto db.createCollection('apartments') para crear la coleccion
    Ejecuto db.apartments.insert({name:'Apartment with 2 bedrooms', capacity:4}) para insertar el documento.
    db.apartments.find() devuelve el documento insertado con los atributos que le indicamos y ademas un atributo llamado '_id' que contiene el id del documento, el cual se genera automaticamente.

6)
Busque los departamentos:

- con capacidad para 3 personas.

```
> db.apartments.find({capacity: { $eq: 3 }})
{ "_id" : ObjectId("5b3685d9061815b8c9093c43"), "name" : "New Apartment", "capacity" : 3, "services" : [ "wifi", "ac" ] }
{ "_id" : ObjectId("5b36861a061815b8c9093c45"), "name" : "1950s Apartment", "capacity" : 3 }
```
- con capacidad para 4 personas o más

```
> db.apartments.find({capacity: { $gte: 4 }})
{ "_id" : ObjectId("5b3683fd061815b8c9093c42"), "name" : "Apartment with 2 bedrooms", "capacity" : 4 }
{ "_id" : ObjectId("5b368617061815b8c9093c44"), "name" : "Nice apt for 6", "capacity" : 6, "services" : [ "parking" ] }
{ "_id" : ObjectId("5b36861d061815b8c9093c46"), "name" : "Duplex Floor", "capacity" : 4, "services" : [ "wifi", "breakfast", "laundry" ] }
```
- con wifi

```
> db.apartments.find({services: {$in: ["wifi"]}})
{ "_id" : ObjectId("5b3685d9061815b8c9093c43"), "name" : "New Apartment", "capacity" : 3, "services" : [ "wifi", "ac" ] }
{ "_id" : ObjectId("5b36861d061815b8c9093c46"), "name" : "Duplex Floor", "capacity" : 4, "services" : [ "wifi", "breakfast", "laundry" ] }
```
- que incluyan la palabra ‘Apartment’ en su nombre

```
> db.apartments.find({name: {$regex: ".*Apartment.*"}})
{ "_id" : ObjectId("5b3683fd061815b8c9093c42"), "name" : "Apartment with 2 bedrooms", "capacity" : 4 }
{ "_id" : ObjectId("5b3685d9061815b8c9093c43"), "name" : "New Apartment", "capacity" : 3, "services" : [ "wifi", "ac" ] }
{ "_id" : ObjectId("5b36861a061815b8c9093c45"), "name" : "1950s Apartment", "capacity" : 3 }
```
- con la palabra ‘Apartment’ en su nombre y capacidad para más de 3 personas

```
> db.apartments.find({$and: [{name: {$regex: ".*Apartment.*"}}, {capacity: { $gt: 3 }}]})
{ "_id" : ObjectId("5b3683fd061815b8c9093c42"), "name" : "Apartment with 2 bedrooms", "capacity" : 4 }
```
- sin servicios (es decir, que el atributo esté ausente)

```
> db.apartments.find({services: {$exists: false}})
{ "_id" : ObjectId("5b3683fd061815b8c9093c42"), "name" : "Apartment with 2 bedrooms", "capacity" : 4 }
{ "_id" : ObjectId("5b36861a061815b8c9093c45"), "name" : "1950s Apartment", "capacity" : 3 }
```

vuelva a realizar la última consulta pero proyecte sólo el nombre del departamento en los resultados, omitiendo incluso el atributo _id de la proyección.

```
> db.apartments.find({services: {$exists: false}}, {name: 1, _id: 0})
{ "name" : "Apartment with 2 bedrooms" }
{ "name" : "1950s Apartment" }
```

7) Actualice el “Duplex Floor” asignándole capacidad 5.
```
> db.apartments.update(
    { name: { $eq: "Duplex Floor" } },
    { $set: { capacity: 5 } }
)

WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
```

8) Agregue “laundry” al listado de services del “Nice apt for 6”.
```
> db.apartments.update(
    { name: { $eq: "Nice apt for 6" } },
    { $push: { services: "laundry" } }
)

WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
```

9) Agregue una persona más de capacidad a todos los departamentos con wifi.
```
> db.apartments.update(
    { services: { $in: [ "wifi" ] } }, 
    { $inc: { capacity: 1 } }, 
    { multi: true }
)

WriteResult({ "nMatched" : 2, "nUpserted" : 0, "nModified" : 2 })
```


--------------------

Parte 3: Índices

▶︎ Elimine todos los departamentos de la colección. Guarde en un archivo llamado ‘generador.js’ el siguiente código JavaScript y ejecútelo con: load(<ruta del archivo ‘generador.js’>). 

```
db.apartments.remove({})
load('generador.js')
```

10) Busque en la colección de departamentos si existe algún índice definido.

```
> db.apartments.getIndexes()
[
        {
                "v" : 2,
                "key" : {
                        "_id" : 1
                },
                "name" : "_id_",
                "ns" : "airbdb.apartments"
        }
]
```

11) Cree un índice para el campo name. Busque los departamentos que tengan en su nombre el string “11” y utilice el método explain("executionStats") al final de la consulta, para comparar la cantidad de documentos examinados y el tiempo en milisegundos de la consulta con y sin índice.

```
> db.apartments.createIndex( { name: 1 } )
{
        "createdCollectionAutomatically" : false,
        "numIndexesBefore" : 1,
        "numIndexesAfter" : 2,
        "ok" : 1
}

> db.apartments.find( { name: { $regex: ".*11.*" } } ).explain( "executionStats" )

Sin indice: 
    {
        ..., 
        "executionStats": {
            ..., 
            "executionTimeMillis" : 31,
            "totalDocsExamined" : 50000,
        }, 
    }

Con indice
    {
        ..., 
        "executionStats": {
            ..., 
            "executionTimeMillis" : 57,
            "totalDocsExamined" : 2291,
        }, 
    }
```

12) Busque los departamentos dentro de la ciudad de Londres. Para esto, puede obtener el polígono del archivo provisto greaterlondon.geojson y definir una variable en la terminal para facilitar la consulta. Cree un índice geoespacial de tipo 2dsphere para el campo location de la colección apartments y, de la misma forma que en el punto 11, compare la performance de la consulta con y sin dicho índice.

```
db.collection.createIndex( { location: "2dsphere" } )
// Edito el archivo, agrego al principio var londres asi queda guardado en una variable.
load('greaterlondon.geojson')

var query = { location: { $geoWithin: { $geometry: londres } } }
db.apartments.find( query )

// Como los resultados son demasiados, chequeo cuantos resultados devolvio usando 
// db.apartments.find( query ).count()
// Devuelve 12359 resultados

db.apartments.find( query ).explain( "executionStats" )

Sin indice:
    {
        ..., 
        "executionStats": {
            ..., 
            "executionTimeMillis" : 168,
            "totalDocsExamined" : 50000,
        }, 
    }

Con indice:
    {
        ..., 
        "executionStats": {
            ..., 
            "executionTimeMillis" : 132,
            "totalDocsExamined" : 18315,
        }, 
    }
```

------------------------------------------------

Parte 4: Aggregation Framework

▶︎MongoDB cuenta con un Aggregation Framework que brinda la posibilidad de hacer analítica en tiempo real del estilo OLAP (Online Analytical Processing), de forma similar a otros productos específicos como Hadoop o MapReduce. En los siguientes ejercicios se verán algunos ejemplos de su aplicabilidad.

Al igual que en la parte 3, guarde en un archivo llamado ‘generadorReservas.js’ el siguiente código JavaScript y ejecútelo con la función load()

13) Obtenga 5 departamentos aleatorios de la colección.
```
db.apartments.aggregate( [ { $sample: { size: 5 } } ] )

{ "_id" : ObjectId("5b36a03046aa630b24c2a0b6"), "name" : "Apartment 8900", "capacity" : 1, "services" : [ ], "location" : { "type" : "Point", "coordinates" : [ -0.08778747246109275, 51.17182570695927 ] } }
{ "_id" : ObjectId("5b36a03246aa630b24c2b333"), "name" : "Apartment 13633", "capacity" : 5, "services" : [ "parking" ], "location" : { "type" : "Point", "coordinates" : [ 0.09552174634372956, 51.12058446427657 ] } }
{ "_id" : ObjectId("5b36a03146aa630b24c2a96b"), "name" : "Apartment 11129", "capacity" : 1, "services" : [ "wifi", "pool" ], "location" : { "type" : "Point", "coordinates" : [ 0.3393064772701505, 51.43173836985423 ] } }
{ "_id" : ObjectId("5b36a03446aa630b24c2c18b"), "name" : "Apartment 17305", "capacity" : 1, "services" : [ "wifi", "pool" ], "location" : { "type" : "Point", "coordinates" : [ 0.2710458669129857, 51.09687166451552 ] } }
{ "_id" : ObjectId("5b36a02f46aa630b24c290b2"), "name" : "Apartment 4800", "capacity" : 1, "services" : [ "wifi", "parking", "breakfast" ], "location" : { "type" : "Point", "coordinates" : [ -0.3972139932395803, 51.17722096603797 ] } }
```

14) Usando el framework de agregación, obtenga los departamentos que estén a 15km (o menos) del centro de la ciudad de Londres ([-0.127718, 51.507451]) y guárdelos en una nueva colección.
```
var query = {
    location: {
        $nearSphere: {
            $geometry: {
                type: "Point",
                coordinates: [ -0.127718, 51.507451 ]
            },
            $maxDistance: 15000
        }
    }
}
db.apartments.find( query )

// > db.apartments.find( query ).count()
// Cantidad de resultados: 5984
```

15) Para los departamentos hallados en el punto anterior, obtener una colección con cada departamento agregando un atributo reservas que contenga un array con todas sus reservas.
Note que sólo es posible ligarlas por el nombre del departamento.

▶︎ Si la consulta se empieza a tornar difícil de leer, se pueden ir guardando los agregadores en variables, que no son más que objetos en formato JSON.

```
var query = [
    {
        $geoNear: {
            near: { type: "Point", coordinates: [ -0.127718, 51.507451 ] },
            distanceField: "distance",
            maxDistance: 15000,
            spherical: true
        }
    },
    {
        $lookup: {
            from: "reservations",
            localField: "name",
            foreignField: "apartmentName",
            as: "reservations"
        }
    },
]
db.apartments.aggregate( query )

Un ejemplo
var query = [
    {
        $geoNear: {
            near: { type: "Point", coordinates: [ -0.127718, 51.507451 ] },
            distanceField: "distance",
            maxDistance: 15000,
            spherical: true
        }
    },
    {
        $lookup: {
            from: "reservations",
            localField: "name",
            foreignField: "apartmentName",
            as: "reservations"
        }
    },
    {
        $match: {
            name: "Apartment 6804"
        }
    }
]
db.apartments.aggregate( query ).pretty()

{
    "_id" : ObjectId("5b36a03046aa630b24c29886"),
    "name" : "Apartment 6804",
    "capacity" : 4,
    "services" : [
        "wifi"
    ],
    "location" : {
        "type" : "Point",
        "coordinates" : [
            -0.12430908219491499,
            51.50681448705283
        ]
    },
    "distance" : 246.59204245386144,
    "reservations" : [
        {
            "_id" : ObjectId("5b36aae646aa630b24c35899"),
            "apartmentName" : "Apartment 6804",
            "from" : ISODate("2014-08-22T20:01:14.675Z"),
            "to" : ISODate("2014-08-25T20:01:14.675Z"),
            "amount" : 463.23
        },
        {
            "_id" : ObjectId("5b36aae646aa630b24c3589a"),
            "apartmentName" : "Apartment 6804",
            "from" : ISODate("2012-05-22T19:18:47.832Z"),
            "to" : ISODate("2012-05-29T19:18:47.832Z"),
            "amount" : 1138.13
        }
    ]
}
```

16) Usando la colección del punto anterior, obtenga el promedio de precio pagado por reserva (precio completo, no dividir por la cantidad de noches) de cada departamento.

```
// Aclaracion, dejo solo los que tienen apartments que tienen reservas porque si no tiene ninguna quedan con null. Lo saco en el stage $unwind, en la linea preserveNullAndEmptyArrays: false

var less15km = { 
    $geoNear: { 
        near: { type: "Point", coordinates: [ -0.127718, 51.507451 ] },  
        distanceField: "distance",  
        maxDistance: 15000,  
        spherical: true
    } 
}

var addApts = { 
    $lookup: {  
        from: "reservations",  
        localField: "name",  
        foreignField: "apartmentName",  
        as: "reservations"
    } 
}

var unwindRes = { 
    $unwind: {  
        path: "$reservations",  
        preserveNullAndEmptyArrays: false
    } 
}

var calcAvgPrice = { 
    $group: {  
        _id: "$name",  
        averagePrice: { 
            $avg: "$reservations.amount" 
        }
    } 
}

var query = [ less15km, addApts, unwindRes, calcAvgPrice ]

db.apartments.aggregate( query )
```

