//load("C:/codigo/Tests/mongo-java/test_consultas.js");

// Test consulta 1
detallesYTotalesDeVentas(new Date(2020, 5, 15), new Date());     // cadena completa
detallesYTotalesDeVentas(new Date(2020, 5, 15), new Date(), 3);     // sucursal 3

// Test consulta 3
detallesYTotalesDeVentasPorMedioDePago(new Date(2020, 5, 5), new Date()); // cadena completa
detallesYTotalesDeVentasPorMedioDePago(new Date(2020, 4, 5), new Date(), 1); // sucursal 2

// Test consulta 5
rankingDeProductosPorMonto(new Date(2020, 1, 1), new Date());    // cadena completa
rankingDeProductosPorMonto(new Date(2020, 1, 1), new Date(), 1); // sucursal 1

// Test consulta 7
rankingDeClientesPorMonto(new Date(2020, 1, 1) , new Date());    // cadena completa
rankingDeClientesPorMonto(new Date(2020, 1, 1) , new Date(), 2);    // sucursal 2
