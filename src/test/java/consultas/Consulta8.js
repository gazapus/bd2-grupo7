conn = new Mongo();
db = conn.getDB("myDatabase");
db = connect("localhost:27017/prueba");
db = db.getSiblingDB('prueba');

cursor = db.ventas.aggregate([
    {
        $match: {
            fecha: {
                $gt: "2019-03-10", $lt: "2021-05-01"
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
                cliente: "$cliente.dni"
            },
            cantidad: {
                "$sum": "$items.cantidad"
            }
        }
    },
    {
        $group: {
            _id: {
                cliente: "$_id.cliente"
            },
            total: { 
                "$sum": "$cantidad"
            },
            cantidad_por_sucursal: {
                "$addToSet": {
                    sucursal : "$_id.sucursal",
                    cantidad : "$cantidad"
                }
            }
        }
    },
    {
        $sort: {
            total: 1
        }
    }
]);

while ( cursor.hasNext() ) {
   printjson( cursor.next() );
}