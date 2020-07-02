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
          $unwind: "$items"
     },
     {
          $group: {
               _id: {
                    sucursal: "$sucursal.nombre",
                    producto: "$items.producto.descripcion"
               },
               monto: { 
                    $sum: { $multiply: ["$items.producto.precio", "$items.cantidad"] } 
               }
          }
     },
     {
          $group: {
               _id: {
                    producto: "$_id.producto"
               },
               monto_en_cadena_completa: {
                    "$sum": "$monto"
               },
               monto_por_sucursal: {
                    "$addToSet": {
                         sucursal: "$_id.sucursal",
                         monto: "$monto"
                    }
               }
          }
     },
     {
          $sort: {
               monto_en_cadena_completa: -1
          }
     }
]);

while (cursor.hasNext()) {
     print(tojson(cursor.next()));
}
