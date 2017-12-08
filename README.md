# Euresty

Instrucciones:

Instalar git

Descargar el proyecto, para descargarlo se abre una consola y se escribe "git clone https://github.com/ChipiMS/Euresty.git" y lo copían en otro lugar para trabajar en él, porque el que descarguen va a ser el ambiente de producción y el que copien es su ambiente local donde pueden hacer pruebas.

Cuando funciona todo bien en el ambiente local se manda a producción, se hace así:

Se abre una consola donde está el archivo README.md y se escribe "git pull origin master".

Luego se pone lo que agregaron al ambiente local en el ambiente de producción ordenadamente como viene ahí, de preferencia en orden alfabetico por sección para que sea más fácil detectar errores, sólo se tiene que agregar un botón o imagen que sirva para llamar su parte en el FXML y en el controlador se declara, se llama la función que ejecuta su parte en los listeners, se agregar el listener al botón o imagen, y se agrega la función que haga todo lo importante, todo eso está documentado en el código y se puede tomar como ejemplo todo lo referente a saveBtn y textBtn.

Después se prueba el ambiente de producción.

Cuando todo está bien se pone en la consola "git add *" y luego Luego poner "git commit -m "Aquí pones una descripción de lo que agregaste"" y luego "git push origin master".

Notas: Con la variable g se agregan cosas al canvas, las imagenes se guardan en src/images.
