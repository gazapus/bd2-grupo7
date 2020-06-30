### Carga de base de datos
Para cargar la base de datos correr la clase CargaBD.java ubicada en src/main/test/java

### Ejecutar consultas*
Las consultas se encuentran el archivo **consultas.js** y cada consulta se encuentra dentro de una funcion
  
Para correr las consultas primero hay que cargarlas al cliente de mongo, puede hacerse desde la consola del mismo mediante el comando: 
>load("{path}/consultas.js)
  
Luego pueden llamarse a las consultas como funciones pasando los parametros necesarios o también puede ejecutar los test de las consultas que se encuentran ene el archivo **testConsultas.js** a través del mismo comando _load("{path}/test_consultas.js")_
---
(*) A confirmar
