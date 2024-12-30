# PruebaTecnica
Para emplear el JenkinsFile del punto 2 se requiere el DcokerFile dentro del directorio, ademas de indicar dentro de las variables de Jenkins las credenciales. Es recomendable manejarlo como variables sensibles a tratarse de credenciales. Tambien se requiere la instalación previa en el servidor de los paquetes necesarios de kubernetes, y el archivo project.yaml con la configuración de cluster para que sea aplicado con el comando apply.

Para el punto 3 se requiere el archivo .env con el valor de las variables. Tener en cuenta la ruta indicada en los volumenes .