var db = connect('127.0.0.1:27017/farmacia');

cursor = db.ventas.aggregate([
     {
          $addFields: {
               fecha: {
                    "$toDate": "$fecha"
               }
          }
     },
     {
          $match: {
               fecha: {
                    $gt: new Date(2020, 1, 1),
                    $lt: new Date()
               }
          }
     },
     {
          $group: {
               _id: {
                    cliente: "$cliente",
                    sucursal: "$sucursal.nombre"
               },
               monto: {
                    $sum: "$total"
               }
          }
     },
     {
          $group: {
               _id: {
                    cliente: "$_id.cliente"
               },
               monto_en_cadena_completa: {
                    $sum: "$monto"
               },
               montos_por_sucursal: {
                    "$addToSet": {
                         sucursal: "$_id.sucursal",
                         monto: "$monto"
                    }
               }
          }
     },
     {
          $project: {
               _id: 0,
               cliente: {
                    $concat: ["$_id.cliente.nombre", " ", "$_id.cliente.apellido"]
               },
               monto_en_la_cadena: "$monto_en_cadena_completa",
               montos_por_sucursal: "$montos_por_sucursal"
          }
     },
     {
          $sort: {
               monto_en_la_cadena: -1
          }
     }
]);

while (cursor.hasNext()) {
     print(tojson(cursor.next()));
}
