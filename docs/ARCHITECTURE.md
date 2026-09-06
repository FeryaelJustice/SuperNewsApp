# Arquitectura del Sistema y Tecnologías - SuperNewsApp

Este documento describe la arquitectura de software, la separación de responsabilidades y las decisiones técnicas adoptadas en **SuperNewsApp**.

---

## 1. Patrón Arquitectónico: Clean Architecture + MVVM

La aplicación sigue los principios de **Clean Architecture** estructurada en tres capas concéntricas con estricta regla de dependencia hacia adentro:

```
+-------------------------------------------------------------+
|                     Capa de Presentación                    |
|      (Jetpack Compose, ViewModels, Navigation 3, Themes)    |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                      Capa de Dominio                        |
|       (Casos de Uso / Interactors, Modelos, Repositorios)   |
+-------------------------------------------------------------+
                              ^
                              |
+-------------------------------------------------------------+
|                       Capa de Datos                         |
|   (Retrofit REST API, Room Database, DataStore, DTOs, Mappers)
+-------------------------------------------------------------+
```

### 1.1 Capa de Dominio (`domain`)
- Totalmente independiente de librerías de UI o Android SDK directo.
- Define los modelos de negocio: `Article`, `Source`.
- Define los contratos de repositorios: `NewsRepository`, `LocalUserManager`.
- Contiene los casos de uso (`usecase`):
  - `GetNews`: Obtiene noticias remotas mediante flujo paginado `PagingSource`.
  - `SearchNews`: Búsqueda de noticias por término.
  - `GetSavedArticles`: Flujo de artículos guardados en base de datos local.
  - `GetSavedArticle`: Consulta si un artículo específico ya está guardado.
  - `UpsertArticle`: Inserta o actualiza un artículo en marcadores.
  - `DeleteArticle`: Elimina un artículo de marcadores.
  - `SaveAppEntry` / `ReadAppEntry`: Control de si el usuario ha completado el Onboarding.

### 1.2 Capa de Datos (`data`)
- Implementa los contratos definidos por el dominio: `NewsRepositoryImpl`, `LocalUserManagerImpl`.
- **Remoto**: `NewsApi` configurado con Retrofit 3.0.0 y serializador Kotlinx Serialization. Consume el servicio de NewsAPI.
- **Local**: `NewsDatabase` con Room 2.8.4, `NewsDao` para transacciones CRUD y `NewsTypeConverter` para serializar tipos complejos como `Source`.
- **Preferencias**: Jetpack DataStore Preferences para almacenar flags ligeros de sesión.

### 1.3 Capa de Presentación (`presentation`)
- Construida íntegramente de forma declarativa con **Jetpack Compose** y **Material Design 3**.
- Cada pantalla cuenta con su correspondiente `ViewModel` inyectado mediante Hilt (`@HiltViewModel`).
- Los estados de pantalla se exponen mediante `StateFlow` o estados mutables de Compose, garantizando inmutabilidad y reactividad ante eventos de usuario.

---

## 2. Inyección de Dependencias (Hilt)

Se utiliza **Hilt** con procesador de símbolos **KSP** (*Kotlin Symbol Processing*) organizado en módulos especializados en el paquete `di`:
- `AppModule`: Provee instancias Singleton de `Retrofit`, `NewsApi`, `NewsDatabase` y `NewsDao`.
- `ManagerModule`: Provee la abstracción de `LocalUserManager`.
- `RepositoryModule`: Vincula `NewsRepository` con su implementación concreta `NewsRepositoryImpl`.
- Calificadores personalizados (`@NewsApiKey`, `@DeeplApiKey`) para inyección segura de credenciales.

---

## 3. Navegación Moderna (Jetpack Navigation 3)

La aplicación utiliza la versión más reciente de **Navigation 3** (`androidx.navigation3`), ofreciendo:
- Gestión de rutas fuertemente tipadas mediante clases selladas (`Route.kt`).
- Pila de retroceso basada en listas inmutables observables (`NavigationViewModel`).
- Soporte para layouts adaptativos (`DeviceType.Compact`, `DeviceType.Medium`, `DeviceType.Expanded`) con `NavigationRail` para tablets o pantallas grandes y `NewsBottomNavigation` para móviles.

---

## 4. Gestión de Listas Infinitas (Paging 3)

La carga de artículos se realiza mediante **Jetpack Paging 3**:
- `NewsPagingSource`: Gestiona la paginación remota por bloques de 10 elementos.
- `cachedIn(viewModelScope)`: Conserva los datos cargados en memoria ante recomposiciones o rotaciones.
- `collectAsLazyPagingItems()`: Conexión nativa con `LazyColumn` en Compose.
