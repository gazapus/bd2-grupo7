var db = connect('127.0.0.1:27017/farmacia');

function traerFechaCorta(fecha) {
     return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear();
}

// Consulta 5
// Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto. 
function rankingDeProductosPorMonto(desde, hasta, codigoSucursal = { $exists: true }) {
     var cursor = db.ventas.aggregate(
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
                         $gte: desde,
                         $lt: hasta
                    },
                    "sucursal.codigo": codigoSucursal
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
                    monto: 1
               }
          },
          {
               $sort: {
                    monto: -1
               }
          }
     );
     let sucursal = (typeof codigoSucursal === 'number') ? ("sucursal " + codigoSucursal) : "cadena completa";
     print("Productos mas vendidos de la " + sucursal + " por monto entre " +
          traerFechaCorta(desde) + " y " + traerFechaCorta(hasta));
     while (cursor.hasNext()) {
          print(tojson(cursor.next()));
     }
}