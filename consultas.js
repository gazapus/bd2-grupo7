var db = connect('127.0.0.1:27017/farmacia');

function traerFechaCorta(fecha) {
     return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear();
}

// Consulta 1
// Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. 
function detallesYTotalesDeVentas(desde, hasta, codigoSucursal = { $exists: true }) {
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
                         $gt: desde,
                         $lt: hasta,
                    },
                    "sucursal.codigo": codigoSucursal
               }
          },
          {
               $group: {
                    _id: {
                         "sucursal": "$sucursal.nombre",
                    },
                    ventas: {
                         $addToSet: {
                              total: "$total",
                              detalle_venta: "$items"
                         }
                    }
               }
          },
          {
               $project: {
                    _id: 0,
                    sucursal: "$_id.sucursal",
                    ventas: 1
               }
          }
     );
     let sucursal = (typeof codigoSucursal === 'number') ? ("sucursal " + codigoSucursal) : "cadena completa";
     print("Detalles y totales de la " + sucursal + " entre " +
          traerFechaCorta(desde) + " y " + traerFechaCorta(hasta));
     while (cursor.hasNext()) {
          print(tojson(cursor.next()));
     }
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
                         "producto": "$items.producto"
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

// Consulta 7
// Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto. 
function rankingDeClientesPorMonto(desde, hasta, codigoSucursal = { $exists: true }) {
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
               $group: {
                    _id: {
                         "cliente": "$cliente"
                    },
                    monto: { $sum: "$total" }
               }
          },
          {
               $project: {
                    _id: 0,
                    cliente: "$_id.cliente",
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
     print("Rankin de clientes por monto de la " + sucursal + " entre " +
          traerFechaCorta(desde) + " y " + traerFechaCorta(hasta));
     while (cursor.hasNext()) {
          print(tojson(cursor.next()));
     }
}