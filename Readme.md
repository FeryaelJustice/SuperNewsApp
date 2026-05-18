# SuperNewsApp

SuperNewsApp es la app diseñada para mantenerte al día con las noticias, con la interfaz creada íntegramente con Jetpack Compose.

## Visión general

- MVVM estructurado en capas Clean Architecture con separación clara entre dominio, datos e interfaz
- Inyección de dependencias mediante Hilt con KSP y calificadores personalizados para credenciales
- Retrofit, Paging y Room para consumir servicios REST y gestionar caché local de artículos
- DataStore Preferences para recordar el flujo de onboarding y ajustes de usuario
- Interfaz Material 3 con navegación Compose, componentes reutilizables y soporte para español y modo nocturno
- Catálogo de versiones Gradle que unifica plugins y dependencias para facilitar actualizaciones

## Requisitos de desarrollo

- Android Studio Koala Feature Drop (2024.1.2) o superior con Android Gradle Plugin 8.11.1
- Gradle 8.14.2 (incluido mediante wrapper) y Kotlin 2.2.0 habilitado para K2 y Compose Compiler
- JDK 21 configurado en el IDE para compilar con `sourceCompatibility` y `targetCompatibility` 21
- Android SDK 36 instalado junto con las Platform Tools y Build Tools recientes
- Dispositivos o emuladores con Android 8.0 (API 26) o superior, que coincide con `minSdk`

## Configuración previa

1. Clona el repositorio y abre la carpeta raíz desde Android Studio (`File > Open`).
2. Verifica que `local.properties` contenga el `sdk.dir` de tu instalación del SDK de Android.
3. Añade tus claves en `local.properties`:

   ```text

   api_key=TU_API_KEY_DE_NEWSAPI
   deepl_api_key=TU_API_KEY_DE_DEEPL

   ```

   Estas entradas se exponen como `string` en recursos para los módulos Hilt de `AppModule`.
4. Sincroniza Gradle (`Sync Now`) para descargar dependencias y generar código con KSP.

## Cómo ejecutar

- Desde Android Studio, selecciona la configuración `app`, elige un dispositivo objetivo y pulsa `Run`.
- Para generar un APK sin IDE, ejecuta `./gradlew assembleDebug` (Linux/macOS) o `gradlew.bat assembleDebug` (Windows) en la raíz del proyecto.
- Las variantes de lanzamiento activan ProGuard, reducción de recursos y subida de símbolos de NDK configurada en `build.gradle.kts`.

## Estructura del repositorio

### Raíz

- `app/`: módulo Android principal con código Kotlin, recursos y configuración específica.
- `gradle/` y `gradle.properties`: definen el wrapper y opciones globales de compilación.
- `gradlew` y `gradlew.bat`: scripts para ejecutar tareas de Gradle sin instalarlo.
- `contact.html`: recurso estático de soporte utilizado en la sección de contacto.
- `settings.gradle.kts` y `build.gradle.kts`: coordinan plugins, catálogos de versiones y módulos incluidos.

### Módulo app

- `build.gradle.kts`: activa Compose, Hilt, Room, Paging, Retrofit y configura claves con `gradleLocalProperties`.
- `proguard-rules.pro`: reglas adicionales para ofuscación en el build de lanzamiento.
- `src/`: contiene los paquetes de código (`main`), pruebas unitarias (`test`) y pruebas instrumentadas (`androidTest`).

### Paquetes principales `app/src/main/java/com/feryaeljustice/supernewsapp`

- `annotations`: define calificadores `@NewsApiKey` y `@DeeplApiKey` para diferenciar inyecciones de cadenas.
- `data/local`: base de datos Room, DAO y conversores para almacenar artículos guardados.
- `data/manager`: implementación de `LocalUserManager` con DataStore para el flujo de onboarding.
- `data/remote`: interfaz `NewsApi` y `PagingSource` que consumen el backend REST mediante Retrofit.
- `data/remote/dto`: modelos de transferencia alineados con las respuestas del servicio.
- `data/repository`: implementación concreta de `NewsRepository` que combina remoto y local.
- `di`: módulos Hilt (`AppModule`, `ManagerModule`, `RepositoryModule`) que exponen dependencias de ámbito `Singleton`.
- `domain/manager`: contratos que abstraen el almacenamiento de preferencias.
- `domain/model`: modelos de dominio consumidos por la capa de presentación.
- `domain/repository`: interfaz del repositorio de noticias accesible desde los casos de uso.
- `domain/usecase`: casos de uso como `GetNews`, `SearchNews`, `UpsertArticle` o `SaveAppEntry`.
- `domain/util`: utilidades compartidas, por ejemplo, transformaciones de cadenas.
- `presentation/bookmark`, `home`, `newsDetail`, `onboarding`, `search`, `contact`: pantallas Compose feature-first con sus `ViewModel`.
- `presentation/navigation`: gráfico de navegación compuesto con destinos y componentes auxiliares.
- `presentation/common`: elementos reutilizables como estados de carga, chips o listas.
- `ui/theme`: colores, tipografías y estilos Material 3 adaptados al branding de MiraiLink.
- `util`: constantes (`Constants`) y envoltorios de estado (`DataState`) compartidos.

### Recursos `app/src/main/res`

- `values` y `values-es`: strings y estilos base con localización al español.
- `values-night`: paleta nocturna para temas dinámicos.
- `drawable` y `font`: assets estáticos utilizados por la interfaz Compose.
- Carpetas `mipmap-*`: iconografía para distintos densidades.
- `xml`: configuraciones como `backup_rules.xml` o definiciones de datos seguros.

### Gradle y catálogo de versiones

- `gradle/libs.versions.toml`: centraliza versiones de AGP 8.11.1, Kotlin 2.2.0, Compose BOM 2025.07.00, Hilt 2.57, Retrofit 3.0.0, Room 2.7.2, Paging 3.3.6, Coil 2.7.0 y Google Play Integrity 1.4.0.
- Los plugins se declaran con alias (`alias(libs.plugins.android.application)`) para mantener consistencia y facilitar actualizaciones.

## Dependencias clave

- Jetpack Compose Material 3 y Navigation Compose para UI declarativa y navegación basada en gráficos.
- Hilt con KSP para inyección de dependencias y enlaces automáticos de componentes.
- Retrofit con convertidor Gson para consumo de servicios REST expuestos por el backend ExpressJS.
- Room y Paging que manejan la persistencia local de artículos y listas infinitas.
- DataStore Preferences para persistir flags ligeros, como la entrada al onboarding.
- Coil para carga de imágenes eficiente dentro de composables.
- Google Play Integrity como protección adicional en builds de producción.

## Pruebas y automatización

- `src/test/java`: pruebas unitarias basadas en JUnit para ViewModels y casos de uso.
- `src/androidTest/java`: pruebas instrumentadas con Espresso y Compose Testing para validaciones UI.
- Tareas recomendadas: `./gradlew testDebugUnitTest` para unit tests y `./gradlew connectedDebugAndroidTest` con un dispositivo/emulador conectado.

## Notas de diseño

Cada pantalla Compose debe iniciar su árbol con un contenedor que aplique `padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)` y `statusBarsPadding()` para evitar que la barra de estado solape el contenido.
