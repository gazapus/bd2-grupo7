// 1. Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. 
// 1.1 Detalle de ventas para la cadena completa entre fechas. 

// 1.2 Totales de ventas para la cadena completa entre fechas. 

// 1.3 Detalle de ventas por sucursal entre fechas. 

// 1.4 Totales de ventas por sucursal entre fechas.

// 5. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto. 
// 5.1 Ranking de ventas de productos en total de la cadena, entre fechas, por monto. 
db.ventas.aggregate(
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
                    $gte: ISODate("2020-01-01T00:00:00.000Z"),
                    $lt: ISODate("2020-06-28T00:00:00.000Z")
               }
          }
     },
     {
          $unwind: "$items"
     },
     {
          $group: {
               _id: {
                    "producto": "$items.producto.descripcion"
               },
               monto: { $sum: { $multiply: ["$items.producto.precio", "$items.cantidad"] } }
          }
     },
     {
          $project: {
               _id: 0,
               producto: "$_id.producto",
               total: "$monto"
          }
     },
     {
          $sort: {
               total: -1
          }
     }  
)

// 5.1 Ranking de ventas de productos por sucursal, entre fechas, por monto. 
db.ventas.aggregate(
     {
          $addFields: {
               fecha: {
                    "$toDate": "$fecha"
               }
          }
     },
     {
          $match: {
               "sucursal.codigo": 1,
               fecha: {
                    $gte: ISODate("2020-01-01T00:00:00.000Z"),
                    $lt: ISODate("2020-06-28T00:00:00.000Z")
               }
          }
     },
     {
          $unwind: "$items"
     },
     {
          $group: {
               _id: {
                    "producto": "$items.producto.descripcion"
               },
               monto: { $sum: { $multiply: ["$items.producto.precio", "$items.cantidad"] } }
          }
     },
     {
          $project: {
               _id: 0,
               producto: "$_id.producto",
               total: "$monto"
          }
     },
     {
          $sort: {
               total: -1
          }
     }  
)