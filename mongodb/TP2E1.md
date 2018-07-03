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

10. Busque en la colección de departamentos si existe algún índice definido.

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

11. Cree un índice para el campo name. Busque los departamentos que tengan en su nombre el string “11” y utilice el método explain("executionStats") al final de la consulta, para comparar la cantidad de documentos examinados y el tiempo en milisegundos de la consulta con y sin índice.

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

12. Busque los departamentos dentro de la ciudad de Londres. Para esto, puede obtener el polígono del archivo provisto greaterlondon.geojson y definir una variable en la terminal para facilitar la consulta. Cree un índice geoespacial de tipo 2dsphere para el campo location de la colección apartments y, de la misma forma que en el punto 11, compare la performance de la consulta con y sin dicho índice.

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

13. Obtenga 5 departamentos aleatorios de la colección.
```
db.apartments.aggregate( [ { $sample: { size: 5 } } ] )

{ "_id" : ObjectId("5b36a03046aa630b24c2a0b6"), "name" : "Apartment 8900", "capacity" : 1, "services" : [ ], "location" : { "type" : "Point", "coordinates" : [ -0.08778747246109275, 51.17182570695927 ] } }
{ "_id" : ObjectId("5b36a03246aa630b24c2b333"), "name" : "Apartment 13633", "capacity" : 5, "services" : [ "parking" ], "location" : { "type" : "Point", "coordinates" : [ 0.09552174634372956, 51.12058446427657 ] } }
{ "_id" : ObjectId("5b36a03146aa630b24c2a96b"), "name" : "Apartment 11129", "capacity" : 1, "services" : [ "wifi", "pool" ], "location" : { "type" : "Point", "coordinates" : [ 0.3393064772701505, 51.43173836985423 ] } }
{ "_id" : ObjectId("5b36a03446aa630b24c2c18b"), "name" : "Apartment 17305", "capacity" : 1, "services" : [ "wifi", "pool" ], "location" : { "type" : "Point", "coordinates" : [ 0.2710458669129857, 51.09687166451552 ] } }
{ "_id" : ObjectId("5b36a02f46aa630b24c290b2"), "name" : "Apartment 4800", "capacity" : 1, "services" : [ "wifi", "parking", "breakfast" ], "location" : { "type" : "Point", "coordinates" : [ -0.3972139932395803, 51.17722096603797 ] } }
```

14. Usando el framework de agregación, obtenga los departamentos que estén a 15km (o menos) del centro de la ciudad de Londres ([-0.127718, 51.507451]) y guárdelos en una nueva colección.
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

15. Para los departamentos hallados en el punto anterior, obtener una colección con cada departamento agregando un atributo reservas que contenga un array con todas sus reservas.
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

16. Usando la colección del punto anterior, obtenga el promedio de precio pagado por reserva (precio completo, no dividir por la cantidad de noches) de cada departamento.

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

