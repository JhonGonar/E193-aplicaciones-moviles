1. ¿Cuál es la función del archivo AndroidManifest.xml?
2. ¿Qué diferencia existe entre `activity_main.xml` y `MainActivity.kt`?
3. ¿Cómo maneja Android los recursos limitados del dispositivo móvil?
4. Mencione 3 aplicaciones famosas que utilizan Kotlin

El archivo README.md debe tener el siguiente formato:

# Taller 1 - Hello Android

## Información del Estudiante
- Nombre: [Nombre completo]
- Código: [Código estudiantil]
- Fecha: [Fecha de entrega]

## Respuestas

### 1. Función del AndroidManifest.xml
El archivo AndroidManifest.xml es el archivo de configuración fundamental de cualquier aplicación Android, ubicado en la raíz del proyecto. Define componentes esenciales (actividades, servicios), permisos de uso (cámara, internet), el nombre del paquete, la versión y los requisitos de hardware/software necesarios para el sistema operativo.

### 2. Diferencia entre activity_main.xml y MainActivity.kt
En el archivo activity_main.xml se estrutura el las etiquetas que renderizan elementos gráficos en la aplicación y que llevan consigo atributos modificadores e identificadores. Luego en el MainActivity.kt se definen variables que interactuan con las etiquetas del archivo xml, las pueden modificar y re renderizar; basicamente define el dinamismo y lógica de interacción con la aplicación.

### 3. Gestión de recursos en Android
Android maneja los recursos limitados del dispositivo (memoria, batería y CPU) mediante un sistema inteligente de administración basado en el ciclo de vida de las aplicaciones.
El sistema operativo asigna mayor prioridad a las aplicaciones que están en primer plano y reduce los recursos de aquellas que están en segundo plano. Cuando el dispositivo necesita liberar memoria, Android puede cerrar procesos que no estén en uso.

### 4. Aplicaciones famosas que usan Kotlin
Kotlin es uno de los lenguajes oficiales para el desarrollo en Android y es utilizado por muchas aplicaciones reconocidas a nivel mundial. Algunas de ellas son:

- Pinterest – Utiliza Kotlin para mejorar la seguridad y reducir errores en su código.
- Trello – Implementó Kotlin para hacer su aplicación más mantenible y eficiente.
- Netflix – Usa Kotlin en el desarrollo de su aplicación Android.
- Uber – Adoptó Kotlin para optimizar su rendimiento y compatibilidad con Java.

Kotlin es recomendado por Google debido a su sintaxis clara, seguridad contra errores comunes y mejor productividad para los desarrolladores.

## Capturas de Pantalla

![Captura del emulador](docs/captura_emulador.png)


## Taller 2 - Arquitectura MVVM

### Respuestas a Preguntas Conceptuales

#### 1. ¿Qué problema resuelve el ViewModel en Android?
El ViewModel se encarga de manejar y guardar los datos que usa la interfaz de la aplicación. Esto evita que los datos se pierdan cuando ocurre un cambio de configuración, como cuando se rota la pantalla del celular. Además, permite separar la lógica de la aplicación de la interfaz, haciendo el código más organizado.

#### 2. ¿Por qué LiveData es "lifecycle-aware" y qué beneficio trae?
LiveData es lifecycle-aware porque tiene en cuenta el ciclo de vida de las Activities y Fragments. Esto significa que solo envía actualizaciones cuando la interfaz está activa. El beneficio es que evita errores, consumo innecesario de recursos y posibles fugas de memoria.

#### 3. Explica con tus propias palabras el flujo de datos en MVVM
En MVVM, la vista (Activity o Fragment) observa los datos que están en el ViewModel. El ViewModel obtiene esos datos a través del Repository, que puede conectarse a una base de datos o a una API. Cuando los datos cambian, LiveData avisa a la vista y la interfaz se actualiza automáticamente.

#### 4. ¿Qué ventaja tiene usar Fragments vs múltiples Activities?
Usar Fragments permite manejar varias pantallas dentro de una misma Activity. Esto hace que la aplicación sea más modular, facilita reutilizar partes de la interfaz y mejora la organización del código.

#### 5. ¿Cómo ayuda el Repository Pattern a la arquitectura?
El Repository Pattern sirve como una capa que organiza el acceso a los datos. El ViewModel obtiene la información del Repository, y este se encarga de decidir si los datos vienen de una base de datos local o de internet. Esto hace que el código sea más fácil de mantener y modificar.
