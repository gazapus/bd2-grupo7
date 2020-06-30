### Carga de base de datos
Para cargar la base de datos correr la clase CargaBD.java ubicada en src/main/test/java

### Ejecutar consultas*
Las consultas se encuentran el archivo **consultas.js** y cada consulta se encuentra dentro de una funcion
  
Para correr las consultas primero hay que cargarlas al cliente de mongo, puede hacerse desde la consola del mismo mediante el comando: 
>load("{path}/consultas.js)  

Luego pueden llamarse a las consultas por el nombre de sus funciones pasando los parametros necesarios o también puede ejecutar el archivo **test_consultas** que ya contiene los test de las consultas mediante el mismo comando _load("{path}/test_consultas.js")_

---
(*) A confirmar
