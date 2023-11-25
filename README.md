# Easytrack - TODO List
Aplicación Android nativa creada para el reto de desarrollador móvil de Easytrack.

## Funcionalidades

La aplicación permite:
- Iniciar y cerrar sesión
- Agregar tareas
- Editar nombre y descripción de tarea
- Marcar una tarea como realizada
- Eliminar tareas

Para esta versión:

* Es posible iniciar sesión con cualquier nombre de usuario y contraseña.
* Las tareas se actualizan a completadas en API al seleccionar el checkbox de la lista.
* La tarea se elimina automáticamente al dar click en el botón de eliminar en el diálogo inferior.
  
## ¿Cómo correr la app?

Para correr la aplicación es necesario contar con Android Studio instalado (https://developer.android.com/studio/install?hl=es-419) y seguir los siguientes pasos:

(Estos pasos fueron probados en Android Studio Giraffe versión 2022.3.1)

- Clonar el repositorio de `https://github.com/NanizMendoza/easytrack-todo-list.git`
- Abrir Android Studio
- Seleccionar `File->Open...`
- Seleccionar el directorio del repositorio clonado
- Dar click en `OK`
- Esperar a que termine la sincronización del Gradle
- Seleccionar un dispositivo o emulador superior a la `API 28` (minSdk)
- Seleccionar `Run -> Run 'app'` (o `Debug 'app'`)
- Ahora puedes probar la app!

## Tecnologías usadas

- Lenguaje Java
- Arquitectura MVVM
- Retrofit para consumo de API
- mockAPI para creación de API
- SharedPreferences para guardar datos de sesión

## Permisos

- Internet: este permiso es requerido para recibir y enviar datos a la API

## Capturas de Pantalla (Light Theme)

<img width="228" alt="Captura de Pantalla 2023-11-24 a la(s) 14 49 24" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/cc0145de-3ad6-4eb1-9a11-503a19e67619">
<img width="231" alt="Captura de Pantalla 2023-11-24 a la(s) 14 50 10" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/1a69b051-22e3-4532-998c-66c03311ad97">
<img width="229" alt="Captura de Pantalla 2023-11-24 a la(s) 14 50 45" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/cdfb0cd3-7a36-4841-a0ad-032c57ddba51">
<img width="231" alt="Captura de Pantalla 2023-11-24 a la(s) 14 52 12" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/c0dfc916-1a64-4eb5-8dbd-94b1dfda4141">

## Capturas de Pantalla (Dark Theme)

<img width="232" alt="Captura de Pantalla 2023-11-24 a la(s) 14 53 47" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/6324a165-7829-43e2-b8e9-f180e2af5e3b">
<img width="231" alt="Captura de Pantalla 2023-11-24 a la(s) 14 54 18" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/dcd1c535-7b7f-4dd7-afd7-8a4cfc9827d8">
<img width="233" alt="Captura de Pantalla 2023-11-24 a la(s) 14 54 51" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/8f0de842-f11a-4dcc-a0af-8bbcdfe912c4">
<img width="231" alt="Captura de Pantalla 2023-11-24 a la(s) 14 55 13" src="https://github.com/NanizMendoza/easytrack-todo-list/assets/124191976/a81bd6fa-e345-4425-aba5-7bebd0cdd64e">
