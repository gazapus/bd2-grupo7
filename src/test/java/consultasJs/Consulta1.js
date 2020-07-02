var db = connect('127.0.0.1:27017/farmacia');

var cursor = db.ventas.aggregate([
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
                    $gt: new Date(2020, 05, 01),
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
                    nroTicket: "$nroTicket",
                    sucursal: "$sucursal.nombre",
                    total: "$total"
               },
               detalles: {
                    $addToSet: {
                         producto: "$items.producto.descripcion",
                         precio: "$items.producto.precio",
                         cantidad: "$items.cantidad",
                    }
               }
          }
     },
     {
          $group: {
               _id: "$_id.sucursal",
               monto_de_ventas: { $sum: "$_id.total" },
               cantidad_de_ventas: { $sum: 1 },
               ventas: {
                    $addToSet: {
                         ticket: "$_id.nroTicket",
                         total: "$_id.total",
                         detalles: "$detalles"
                    }
               }
          }
     },
     {
          $project: {
               _id: 1,
               monto_de_ventas: 1,
               cantidad_de_ventas: 1,
               ventas: 1
          }
     }
]);

while (cursor.hasNext()) {
     print(tojson(cursor.next()));
}
