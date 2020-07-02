conn = new Mongo();
db = conn.getDB("myDatabase");
db = connect("localhost:27017/farmacia");
db = db.getSiblingDB('farmacia');

cursor = db.ventas.aggregate([
    {
        $match: {
            fecha: {
                $gt: "2020-03-10", $lt: "2020-05-01"
            }
        }
    },
    {
        $unwind: "$items"
    },
    {
        $project: {
            ticket: "$nroTicket",
            sucursal: "$sucursal.nombre",
            total: "$total",
            detalle: {
                producto: "$items.producto.descripcion",
                tipo: "$items.producto.tipo.nombre",
                precio: "$items.producto.precio",
                cantidad: "$items.cantidad"
            }
        }
    },
    {
        $group: {
            _id: {
                sucursal: "$sucursal",
                ticket: "$ticket"
            },
            total: {
                "$addToSet": "$total"
            },
            detalle: {
                "$addToSet": "$detalle"
            }
        }
    },
    {
        $unwind: "$total"
    },
    {
        $group: {
            _id: {
                sucursal: "$_id.sucursal"
            },
            total: {
                "$sum": "$total"
            },
            ventas: {
                "$addToSet": {
                    total: "$total",
                    detalle: "$detalle"
                },
            }
        }
    }
]);

while ( cursor.hasNext() ) {
   printjson( cursor.next() );
}