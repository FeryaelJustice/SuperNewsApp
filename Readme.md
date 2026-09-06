# SuperNewsApp

SuperNewsApp es una aplicación nativa moderna para Android diseñada para mantenerte al día con las noticias más relevantes de todo el mundo. Su interfaz está construida íntegramente con **Jetpack Compose**, adoptando los principios de diseño de **Material 3** y una arquitectura escalable **Clean Architecture + MVVM**.

---

## Documentación Técnica Detallada

Para facilitar la comprensión global de la aplicación y orientar futuros desarrollos, el proyecto cuenta con documentación especializada en la carpeta `docs/`:

- [docs/DESIGN_SYSTEM.md](docs/DESIGN_SYSTEM.md): Guía oficial de diseño visual, tokens de color (Modo Claro y Modo Oscuro), tipografía Poppins, escala tipográfica, espaciados y especificaciones de componentes.
- [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md): Arquitectura Clean Architecture, capas de datos, dominio y presentación, inyección con Hilt y flujo reactivo.
- [docs/FEATURES_GUIDE.md](docs/FEATURES_GUIDE.md): Guía detallada de cada pantalla (Onboarding, Inicio, Búsqueda, Guardados, Detalle y Contacto) y flujos de usuario.

---

## Características Principales y Mejoras Visuales

1. **Carrusel Hero Destacado (`FeaturedNewsCarousel`)**:
   - Deslizador horizontal automático (`HorizontalPager`) en la parte superior de Inicio.
   - Tarjetas de alto impacto con fotografía recortada, gradiente de contraste vertical, badge de fuente, titular destacado y tiempo relativo.
   - Píldoras indicadoras de página animadas y pausa automática al interactuar.
   - Acceso directo al detalle del artículo al pulsar la tarjeta.

2. **Cinta de Última Hora Interactiva (`BreakingNewsTicker`)**:
   - Barra estilizada con distintivo `[ 🔴 ÚLTIMA HORA ]` y punto indicador pulsante en vivo.
   - Transición vertical animada entre titulares de actualidad.
   - Totalmente interactiva: un toque sobre el titular en movimiento abre la noticia de inmediato.

3. **Presentación Enriquecida de Noticias (`ArticleCard`)**:
   - Tarjetas Material 3 con esquinas redondeadas de `16.dp` y elevación sutil.
   - Miniatura de `96x96 dp` con animación Shimmer durante la carga e icono de fallback si la URL falla.
   - Formato de tiempo relativo amigable ("Hace 15 min", "Hace 2 h", "Ayer") en lugar de marcas de tiempo ISO crudas.
   - Badge de fuente con color primario.

4. **Pantalla de Detalle Mejorada (`NewsDetailScreen`)**:
   - Fotografía de cabecera con relación de aspecto optimizada.
   - Resumen editorial (`description`) formateado como entradilla destacada en cursiva.
   - Tiempo estimado de lectura calculado automáticamente ("Lectura: 3 min").
   - Botón de acción principal para abrir el artículo original completo en la fuente.

5. **Búsqueda Dinámica con Tendencias (`SearchScreen`)**:
   - Barra de búsqueda con botón de borrado instantáneo ("X").
   - Chips de temas del momento cuando el campo está vacío ("Tecnología", "Ciencia", "Economía", "Deportes", "Salud").

6. **Gestión de Guardados (`BookmarkScreen`)**:
   - Contador en tiempo real de artículos almacenados en local (Room).
   - Estado vacío acogedor con botón de acceso directo para explorar noticias.

7. **Paleta Editorial Material 3 y Modo Oscuro**:
   - Unificación visual con azul editorial (`#1A56DB` / `#4B8BF5`), rojo de alerta (`#EF4444`) y superficies neutras limpias.
   - Compatibilidad nativa con tema claro y modo oscuro profundo.

---

## Requisitos de Desarrollo

- Android Studio Koala Feature Drop (2024.1.2) o superior con Android Gradle Plugin 8.11.1+
- Gradle 8.14.2 (incluido mediante wrapper) y Kotlin con Compose Compiler
- JDK 21 configurado en el IDE para compilar con `sourceCompatibility` y `targetCompatibility` 21
- Android SDK 36 instalado junto con Platform Tools y Build Tools
- Dispositivos o emuladores con Android 8.0 (API 26) o superior (`minSdk` 26)

---

## Configuración Previa

1. Clona el repositorio y abre la carpeta raíz desde Android Studio (`File > Open`).
2. Verifica que `local.properties` contenga la ruta a tu Android SDK (`sdk.dir`).
3. Añade tus claves en `local.properties`:

   ```properties
   api_key=TU_API_KEY_DE_NEWSAPI
   deepl_api_key=TU_API_KEY_DE_DEEPL
   ```

4. Sincroniza el proyecto con los archivos de Gradle (`Sync Now`).

---

## Cómo Ejecutar

- Desde Android Studio, selecciona la configuración `app`, elige un dispositivo objetivo y pulsa `Run` (o `Shift + F10`).
- Para generar el APK de depuración desde la terminal:
  - En Windows: `.\gradlew.bat assembleDebug`
  - En Linux o macOS: `./gradlew assembleDebug`
- Para ejecutar las pruebas unitarias:
  - `.\gradlew.bat testDebugUnitTest`

---

## Estructura del Repositorio

```text
SuperNewsApp/
├── app/
│   ├── build.gradle.kts           # Configuración del módulo, dependencias y plugins
│   └── src/
│       ├── main/
│       │   ├── java/com/feryaeljustice/supernewsapp/
│       │   │   ├── annotations/   # Calificadores de inyección Hilt
│       │   │   ├── data/          # Implementaciones: Room, Retrofit, Paging, DataStore
│       │   │   ├── di/            # Módulos de Hilt (AppModule, ManagerModule, etc.)
│       │   │   ├── domain/        # Modelos de negocio, repositorios y casos de uso
│       │   │   │   └── util/      # DateTimeUtils y transformaciones de texto
│       │   │   ├── presentation/  # Pantallas Compose, componentes UI y ViewModels
│       │   │   │   ├── bookmark/  # Pantalla de artículos guardados
│       │   │   │   ├── common/    # ArticleCard, BreakingNewsTicker, SearchBar, Shimmer
│       │   │   │   ├── contact/   # Pantalla de contacto y políticas de noticias
│       │   │   │   ├── home/      # HomeScreen y FeaturedNewsCarousel
│       │   │   │   ├── navigation/# Navigation 3 y NewsBottomNavigation
│       │   │   │   ├── newsDetail/# Pantalla de detalle de noticia
│       │   │   │   └── search/    # Pantalla de búsqueda y chips de tendencias
│       │   │   └── ui/theme/      # Color.kt, Theme.kt, Type.kt (Poppins M3)
│       │   └── res/               # Drawables, fuentes Poppins, strings localizados
│       └── test/                  # Pruebas unitarias
├── docs/                          # Documentación del proyecto
│   ├── ARCHITECTURE.md            # Arquitectura Clean Architecture + MVVM
│   ├── DESIGN_SYSTEM.md           # Guía oficial de diseño visual, colores y tipografía
│   └── FEATURES_GUIDE.md          # Guía detallada de pantallas y componentes
├── gradle/libs.versions.toml      # Catálogo centralizado de versiones
├── build.gradle.kts               # Configuración raíz de Gradle
├── settings.gradle.kts            # Configuración de repositorios y módulos
└── Readme.md                      # Documento principal del repositorio
```

---

## Dependencias Clave

- **Jetpack Compose & Material 3**: UI moderna, reactiva y completamente declarativa.
- **Navigation 3**: Navegación fuertemente tipada con pila inmutable.
- **Hilt & KSP**: Inyección de dependencias modular y de alto rendimiento.
- **Retrofit & Kotlinx Serialization**: Consumo del API REST de noticias.
- **Room & Paging 3**: Persistencia local de marcadores y paginación reactiva de listas infinitas.
- **DataStore Preferences**: Persistencia de preferencias ligeras de usuario.
- **Coil**: Carga eficiente y asíncrona de imágenes con caché en memoria y disco.
