# Guía de Diseño Visual y Sistema de Estilo - SuperNewsApp

Esta guía establece las directrices oficiales de diseño visual, interfaz de usuario (UI), experiencia de usuario (UX), paleta cromática, escala tipográfica, espaciados y especificaciones de componentes para **SuperNewsApp**. Su propósito es servir como estándar de referencia para mantener la consistencia estética y funcional en toda la aplicación.

---

## 1. Principios de Diseño

1. **Claridad Editorial**: El contenido periodístico es el protagonista. La tipografía, los contrastes y los espacios en blanco deben garantizar una lectura descansada y sin distracciones.
2. **Jerarquía Visual Inmediata**: Diferenciación nítida entre noticias de última hora (*Breaking News*), reportajes destacados (*Hero Slider*) y noticias estándar en lista.
3. **Interactividad Dinámica**: Todo elemento visual que presente información relevante (como cintas de teletipo o carruseles automáticos) debe ser completamente interactivo, permitiendo al lector acceder a la noticia con un solo toque.
4. **Respeto a Material 3 y Modo Oscuro**: Integración nativa con tokens de Material Design 3, soporte fluido para modo claro y modo oscuro OLED de alto contraste.

---

## 2. Paleta de Colores y Tokens

### 2.1 Colores Semánticos Principales

| Token | Modo Claro (HEX) | Modo Oscuro (HEX) | Uso y Rol Semántico |
| :--- | :--- | :--- | :--- |
| **Primary** | `#1A56DB` | `#4B8BF5` | Azul editorial periodístico. Acciones principales, enlaces, indicadores activos. |
| **OnPrimary** | `#FFFFFF` | `#0B1E47` | Texto e iconos sobre color primario. |
| **PrimaryContainer**| `#E8EFFF` | `#1A3268` | Contenedores sutiles de acento primario y píldoras activas. |
| **OnPrimaryContainer**| `#0C2B7A` | `#D6E4FF` | Texto sobre contenedores primarios. |
| **Secondary** | `#374151` | `#9CA3AF` | Color neutro secundario para metadatos, autor y fecha. |
| **Background** | `#F8FAFC` | `#0F172A` | Fondo general de la aplicación. |
| **OnBackground** | `#0F172A` | `#F8FAFC` | Texto principal sobre fondo de la app. |
| **Surface** | `#FFFFFF` | `#1E293B` | Superficie de tarjetas (`Card`), barras de búsqueda y modales. |
| **OnSurface** | `#1E293B` | `#F1F5F9` | Texto principal sobre tarjetas y superficies. |
| **SurfaceVariant** | `#F1F5F9` | `#334155` | Fondos de campos de texto, separadores e indicadores inactivos. |
| **OnSurfaceVariant**| `#64748B` | `#94A3B8` | Textos secundarios, horas relativas y placeholders. |
| **Error / Breaking**| `#EF4444` | `#F87171` | Rojo de alerta y directos: distintivos de "ÚLTIMA HORA" y errores. |
| **OnError** | `#FFFFFF` | `#450A0A` | Texto sobre badges de última hora o estados de error. |

### 2.2 Gradientes y Scrims
- **Hero Scrim (Tarjetas y Carrusel)**: `Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)))`. Asegura que cualquier titular sobre una fotografía conserve un contraste superior a 7:1 (nivel AAA WCAG).
- **Surface Elevation**: En modo oscuro se utiliza una elevación tonal sutil con transparencia para diferenciar la superficie de la tarjeta respecto al fondo `#0F172A`.

---

## 3. Tipografía y Estilos de Texto

La aplicación utiliza la familia tipográfica **Poppins** en tres pesos:
- `Poppins-Regular` (peso 400)
- `Poppins-SemiBold` (peso 600)
- `Poppins-Bold` (peso 700)

### 3.1 Escala Tipográfica (Tokens Material 3)

| Token M3 | Peso | Tamaño (`sp`) | Interlineado (`lineHeight`) | Caso de Uso |
| :--- | :--- | :--- | :--- | :--- |
| `headlineLarge` | Bold (700) | 28 sp | 36 sp | Títulos de portada y pantallas principales. |
| `headlineMedium`| Bold (700) | 22 sp | 30 sp | Titular principal en la pantalla de Detalle. |
| `titleLarge` | SemiBold (600) | 18 sp | 26 sp | Titulares dentro del Carrusel Hero Destacado. |
| `titleMedium` | SemiBold (600) | 16 sp | 22 sp | Encabezados de sección ("Últimas Noticias", "Tendencias"). |
| `titleSmall` | SemiBold (600) | 14 sp | 20 sp | Titular de noticias en tarjeta estándar (`ArticleCard`). |
| `bodyLarge` | Regular (400) | 16 sp | 26 sp | Entradilla editorial (`description`) en pantalla de Detalle. |
| `bodyMedium` | Regular (400) | 14 sp | 22 sp | Cuerpo del artículo (`content`) y mensajes de estado. |
| `bodySmall` | Regular (400) | 12 sp | 18 sp | Textos secundarios de apoyo y sugerencias. |
| `labelLarge` | SemiBold (600) | 14 sp | 20 sp | Texto en botones de acción principal ("Leer más", "Reintentar"). |
| `labelMedium` | SemiBold (600) | 12 sp | 16 sp | Badge de la Fuente de noticias (ej. "BBC News", "Reuters"). |
| `labelSmall` | Regular (400) | 11 sp | 14 sp | Metadatos secundarios (fecha relativa, icono de tiempo). |

---

## 4. Espaciados, Radios y Elevaciones

### 4.1 Escala de Espaciado (`Dimens`)
- `ExtraSmall`: `4.dp` (separación entre icono y texto adyacente).
- `Small`: `8.dp` (separación interna entre elementos relacionados).
- `Medium`: `16.dp` (márgenes estándar de pantallas y tarjetas).
- `Large`: `24.dp` (separación entre secciones principales).
- `ExtraLarge`: `32.dp` (espaciado entre bloques de contenido).

### 4.2 Radios de Esquina (`Shapes`)
- **Píldoras y Badges**: `RoundedCornerShape(50)` o `CircleShape` (Badges de última hora, chips de categorías).
- **Tarjetas de Noticias (`ArticleCard`)**: `RoundedCornerShape(16.dp)` para una apariencia suave y moderna.
- **Carrusel Hero**: `RoundedCornerShape(20.dp)` con recorte de imagen nítido.
- **Campos de Texto y Búsqueda**: `RoundedCornerShape(14.dp)`.
- **Botones de Acción**: `RoundedCornerShape(12.dp)`.

---

## 5. Especificación de Componentes Clave

### 5.1 Carrusel Destacado Hero (`FeaturedNewsCarousel`)
- **Función**: Muestra de 3 a 5 noticias de alto impacto en la parte superior de la pantalla de inicio.
- **Estructura**:
  - `HorizontalPager` con altura fija de `210.dp`.
  - Imagen en alta resolución con `ContentScale.Crop`.
  - Capa de gradiente oscuro en el 60% inferior para máxima legibilidad.
  - Badge de la fuente en la esquina superior izquierda con fondo semitransparente (`Surface` con 85% alpha).
  - Titular en `titleLarge` (color blanco constante) limitado a 2 líneas con puntos suspensivos.
  - Indicador de tiempo relativo ("Hace 15 min") con icono de reloj.
  - Indicadores de puntos inferiores (*Page Indicator*) que se expanden a píldora activa de forma fluida.
  - Temporizador de desplazamiento automático cada 4 segundos, que se congela si el usuario mantiene pulsada la tarjeta o interactúa con ella.
  - **Acción**: Al pulsar la tarjeta, navega directamente a la pantalla de detalle del artículo.

### 5.2 Cinta de Última Hora (`BreakingNewsTicker`)
- **Función**: Informar de noticias urgentes de manera continua.
- **Estructura**:
  - Contenedor con fondo `SurfaceVariant` o borde sutil.
  - Píldora fija a la izquierda: fondo `Error` (rojo) con texto en blanco `ÚLTIMA HORA` y punto indicador.
  - Desplazamiento horizontal continuo o rotación vertical animada de titulares.
  - **Interactividad total**: El titular es pulsable y abre el artículo en pantalla de detalle.

### 5.3 Tarjeta de Noticia Estándar (`ArticleCard`)
- **Función**: Elemento básico del listado de noticias en Inicio, Búsqueda y Guardados.
- **Estructura**:
  - Contenedor `Card` con elevación sutil de `1.dp` y color `Surface`.
  - Miniatura de imagen de `96x96 dp` con esquinas redondeadas de `12.dp`.
  - Placeholder de carga neutro con animación Shimmer (no ilustraciones descontextualizadas).
  - Columna de texto con:
    1. Fila superior: Badge con el nombre de la fuente (`labelMedium`).
    2. Titular principal (`titleSmall` con `fontWeight = SemiBold`, máximo 2 líneas).
    3. Fila inferior: Autor y tiempo relativo ("Hace 2 h").

### 5.4 Pantalla de Detalle (`NewsDetailScreen`)
- **Estructura**:
  - Top Bar con navegación hacia atrás, botón de compartir, marcador de favoritos y botón de abrir en navegador web.
  - Imagen cabecera hero con altura de `250.dp`.
  - Badge de fuente, nombre del autor y fecha completa localizada.
  - Tiempo estimado de lectura calculado automáticamente (ej. "Lectura: 2 min").
  - Titular en `headlineMedium`.
  - Entradilla destacada con `description` en estilo `bodyLarge` y peso `SemiBold`.
  - Cuerpo de la noticia con `content`.
  - Botón de acción: *"Leer artículo completo en [Fuente]"* para visualizar la versión original completa.
