# DOSW-Library

## diagramas
Por un lado vemos Biblioteca Front el cual es la cara del sistema, lo que el usuario ve
y este se encarga de capturar los datos.

por otro lado la Biblioteca Core es el que recibe las peticiones del Front
aplica la logica y e encarga de los errores

![img_11.png](img/img_11.png)

Controladores: recibe una peticion HTTP y las envia al servicio que corresponda
y los MAPPER  lo que hacen es transformar objetos en un formato JSON

Service: se encarga de la logica de cada objeto y le pide ayuda a los validadores para verificar reglas complejas

Mapper:. evitan que expongas datos sensibles de tu base de datos directamente al usuario de Swagger
pero para este caso aun no lo tenemos implementado al igual que los dtos

![img_12.png](img/img_12.png)

Mongo
![mongo.png](img/mongo.png)

# Ejecucion funcionalidades api
## BOOK

![img_7.png](img/img_7.png)
![img_9.png](img/img_9.png)
![img_14.png](img/img_14.png)
![img_15.png](img/img_15.png)
![img_16.png](img/img_16.png)
---
## USER

![img_17.png](img/img_17.png)
![img_18.png](img/img_18.png)
![img_19.png](img/img_19.png)
![img_20.png](img/img_20.png)

---
## LOAN

![img_21.png](img/img_21.png)
![img_22.png](img/img_22.png)
![img_23.png](img/img_23.png)
![img_24.png](img/img_24.png)
![img_25.png](img/img_25.png)
![img_26.png](img/img_26.png)


# ejecución de pruebas de los servicios
![img_10.png](img/img_10.png)
![img_13.png](img/img_13.png)
como vemos en cada uno de los directorios en los que se estan trabajando
lo que se busco fue que se probaran y pasaran cada uno de los test, claramente
hay unos que no esta al 100% pero es por lo que hay algunos directorios que no estan
implemetados como los mappers y lod dtos

# Cobertura y análisis estadísticos
![img_8.png](img/img_8.png)
en esta parte me dice que hay dos errores, el primero es que el nombre de"DOWS-Librari 
no deberia estar en mayuscula y la otra es por lo que la covertura esta en 73.3% pero con 
jacoco tenemos 83%, esto es por lo que los mapper y los dtos aun no estan implmentados

---
se anexo el JSON del swager

---
# video persistencia

https://www.youtube.com/watch?v=7D4nlBOfeTo

# video seguridad

https://www.youtube.com/watch?v=-19HLQSgZGY

# video Reto # 5 - Pruebas funcionales

https://youtu.be/_FBnEqg0YYk

https://youtu.be/UCf-QWhA-iI

# video Reto # 7 - Azure CI/CD

https://youtu.be/Sac58RuvOCc

![img.png](img/img.png)
![img2.png](img/img2.png)

# Reto # 8 - 
[high-level-design.md](docs/high-level-design.md)