
# Backend Challenge

Este proyecto esta desarrollado en Java junto a las tecnologías de SpringBoot, Spring Cloud, JPA, MySQL, RestAssured, Keycloak y Docker


- Hecho por [@moneta-sofia](https://github.com/moneta-sofia)




## Contenidos

- [Demo](#demo)
- [Estructura](#estructura)
- [Funciones](#funciones)
- [Ejecutar en local](#ejecutar-en-local)
- [Tests](#tests)
- [Feedback](#feedback)
- [Otros recursos](#otros-recursos)
- [Mas documentación](#mas-documentacion)




## Demo

![](https://i.imgur.com/RBzOL71.gif)


## Estructura

Este es el diagrama UML final que representa la estructura completa de la base de datos. Incluye todas las entidades, atributos, relaciones y cardinalidades necesarias para el correcto funcionamiento del sistema.
![](https://i.imgur.com/XDcFxc4.png)

Mientras qe en este grafico se puede ver e interpretar la arquitectura general del sistema, que muestra cómo se comunican entre sí los servicios.
![](https://i.imgur.com/JMjvI9P.png)
> [!NOTE]  
> Las flechas con colores representan una interacción mediante protocolos REST, mientras que las punteadas representan una comunicación a través de Feign.
## Funciones

Usuario
- Signup
- Login
- Logout
- Obtener
- Actualizar

Cuenta
- Crear cuenta
- Obtener todas las cuentas
- Obtener cuenta 
- Actualizar cuenta

Transacciones
- Crear deposito
- Crear transferencia
- Obtener transacciones por cuenta
- Obtener transacción
- Obtener ultimos destinos

Tarjetas
- Crear tarjeta
- Obtener tarjetas  Cuenta
- Obtener tarjeta 
- Eliminar tarjeta
## Ejecutar en local

Clona el proyecto

```bash
  git clone https://github.com/moneta-sofia/challengebackend.git
```

Accede al directorio del proyecto clonado

```bash
  cd challengebackend
```
> [!IMPORTANT] 
> Asegúrate de configurar las variables de entorno necesarias para el proyecto.

Las que vas a necesitar para keycloack estan en el archivo [.env](./.env)

Debes agregar las credenciales de una base de datos tuya en las variables
`DB_URL`
`DB_USERNAME`
`DB_PASSWORD`

Por último, levanta los servicios con docker
```
  docker-compose up -d
```

## Tests

[En esta hoja de notion podras encontrar la documentación de los test realizados a lo largo de los sprints](https://creative-smartphone-967.notion.site/Testing-14905bbe7343803fa041cd66717286a2)

Podras encontrar:

**`Tests Exploratorios`** **`Casos de Prueba`**
**`Tests Automatizados (Postman)`**
**`Errores Notificados`**

> [!TIP]
> Si quieres ejecutar los test automatizados de RestAzure deberás descomentar los test de cada servicio  





## Feedback

Si tienes alguna sugerencia del proyecto o de la documentacion te puedes contactar a [mi correo](https://mail.google.com/mail/u/0/?fs=1&to=sofia.moneta.dev@gmail.com&tf=cm)! :D





## Otros recursos

[**`Bucket con servicios dockerizados`**](https://backend-challenge.s3.us-east-2.amazonaws.com/project/index.html)

[**`Archivo DUMP`**](https://file.notion.so/f/f/95577fa7-4ed4-493a-baa0-d238a34cd0af/17e2ff7b-b136-4448-8f2a-af99745ba634/BackendChallenge-DUMP.sql?table=block&id=14805bbe-7343-80d9-abe7-dc44bb1c7461&spaceId=95577fa7-4ed4-493a-baa0-d238a34cd0af&expirationTimestamp=1732744800000&signature=SSRVihJMf1K63pnT5eLww6Wu9joU0zFJ68k1VPsCa34&downloadName=BackendChallenge-DUMP.sql)



## Mas documentacion

Necesitas más información sobre el proyecto? Puedes visitar el notion en donde desarrolle cada aspecto del desafio en este [link](https://creative-smartphone-967.notion.site/BackendChallenge-By-Sofia-Moneta-10f05bbe734380cc8626d1ed3329cd40?pvs=4).