# Guía de Funcionalidades y Pantallas - SuperNewsApp

Este documento describe detalladamente cada pantalla, componente interactivo y flujo de usuario disponible en **SuperNewsApp**.

---

## 1. Flujo de Bienvenida (Onboarding)

- **Objetivo**: Introducir al nuevo usuario a la propuesta de valor de la app la primera vez que se inicia.
- **Componentes**:
  - `HorizontalPager` con 3 páginas ilustradas.
  - Indicador de página animado (`PageIndicator`).
  - Botones contextuales de navegación ("Atrás", "Siguiente", "Comenzar").
- **Persistencia**: Al pulsar "Comenzar", se dispara el evento `OnBoardingEvent.SaveAppEntry`, guardando la preferencia en Jetpack DataStore para que en aperturas posteriores la app inicie directamente en la pantalla principal.

---

## 2. Pantalla de Inicio (Home)

La pantalla principal ha sido rediseñada para ofrecer una experiencia editorial de primer nivel con tres áreas clave:

### 2.1 Cabecera de Marca
- Logo estilizado con la marca **SuperNews**.
- Subtítulo dinámico con la fecha actual formateada en español (ej. "Domingo, 6 de septiembre").

### 2.2 Carrusel Hero Destacado (`FeaturedNewsCarousel`)
- Deslizador horizontal automático que presenta las noticias de mayor impacto.
- Cada tarjeta incluye:
  - Imagen panorámica recortada con gradiente de contraste.
  - Badge flotante con la fuente de la noticia.
  - Titular principal destacado legible sobre la imagen.
  - Indicador de tiempo relativo ("Hace 20 min").
  - Píldoras indicadoras de página animadas.
- **Comportamiento**: Auto-avance cada 4 segundos, pausa automática al interactuar o deslizar manualmente, y navegación directa a la pantalla de detalle al hacer clic.

### 2.3 Cinta de Última Hora Interactiva (`BreakingNewsTicker`)
- Barra con distintivo llamativo `[ 🔴 ÚLTIMA HORA ]`.
- Desplazamiento continuo de titulares urgentes.
- **Interactividad**: Al tocar el titular que está pasando, se abre directamente la pantalla de detalle de ese artículo concreto.

### 2.4 Listado de Últimas Noticias
- Sección con título claro y lista infinita de noticias (`LazyColumn` conectada a Paging 3).
- Tarjetas `ArticleCard` con miniaturas optimizadas, badges de fuente, titular de 2 líneas y fecha relativa.
- Animación de carga *Shimmer* con proporciones idénticas a la tarjeta final.
- Estado de error conectado con botón interactivo de "Reintentar conexión".

---

## 3. Pantalla de Búsqueda (Search)

- **Barra de Búsqueda (`SearchBar`)**:
  - Icono de lupa y campo de texto con esquinas redondeadas.
  - Botón de borrado instantáneo ("X") que aparece al escribir.
  - Teclado con acción IME de búsqueda.
- **Sugerencias de Tendencias**:
  - Cuando el usuario aún no ha escrito nada, se presentan chips interactivos con temas de actualidad (ej. "Tecnología", "Negocios", "Deportes", "Ciencia", "Salud").
  - Al pulsar sobre un chip, se rellena y ejecuta la búsqueda automáticamente.
- **Resultados**: Muestra la lista de artículos encontrados con soporte para paginación infinita y manejo de resultados vacíos.

---

## 4. Pantalla de Favoritos / Guardados (Bookmarks)

- **Objetivo**: Permitir al usuario conservar artículos para lectura posterior sin conexión a internet.
- **Listado Local**: Se sincroniza en tiempo real con la base de datos Room mediante `Flow<List<Article>>`.
- **Estado Vacío Amigable**:
  - Cuando no hay artículos guardados, muestra una ilustración limpia con mensaje explicativo.
  - Botón de acción rápida: *"Explorar noticias"*, que redirige al usuario a la pantalla de Inicio para descubrir contenido.

---

## 5. Pantalla de Detalle de Noticia (NewsDetail)

- **Top Bar**:
  - Botón de retroceso hacia la pantalla anterior.
  - Botón de compartir nativo de Android (`ACTION_SEND`) con enlace del artículo.
  - Botón de guardar/desmarcar de favoritos con icono dinámico y confirmación visual.
  - Botón de abrir en navegador web.
- **Imagen de Cabecera**: Imagen en alta definición con esquinas suavemente redondeadas.
- **Metadatos Editoriales**:
  - Badge destacado de la fuente.
  - Nombre del autor.
  - Fecha de publicación localizada.
  - Estimación automática de tiempo de lectura (ej. "Lectura: 3 min").
- **Contenido del Artículo**:
  - Entradilla o resumen periodístico (`description`) formateado en estilo destacado.
  - Cuerpo de la noticia (`content`) procesado para limpiar sufijos técnicos.
  - Botón de acción principal: *"Leer artículo completo en [Fuente]"*, para consultar la publicación original completa.

---

## 6. Pantalla de Contacto y Soporte (Contact)

- **Cabecera con Gradiente**: Logo e icono distintivo con diseño refinado.
- **Información del Desarrollador**: Tarjetas interactivas para enviar correo, llamar por teléfono o visitar la web oficial.
- **Aviso de Responsabilidad**: Tarjeta informativa indicando la procedencia de los contenidos periodísticos y enlace a la API.
- **Formulario de Mensaje**: Envío directo de comentarios y sugerencias con validación de longitud.
