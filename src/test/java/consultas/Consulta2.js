conn = new Mongo();
db = conn.getDB("myDatabase");
db = connect("localhost:27017/prueba");
db = db.getSiblingDB('prueba');

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
            obra_social: "$cliente.obraSocial.nombre",
            detalle: {
                producto: "$items.producto.descripcion",
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
            obra_social: {
                "$addToSet": "$obra_social"
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
                    obra_social: "$obra_social",
                    total: "$total",
                    detalle: "$detalle"
                },
            }
        }
    },
]);

while ( cursor.hasNext() ) {
   printjson( cursor.next() );
}