# Política de Privacidad de SuperNewsApp

**Última actualización:** 10 de octubre de 2026

Bienvenido a **SuperNewsApp** (en adelante, la "Aplicación"), desarrollada por **FeryaelJustice** (en adelante, el "Desarrollador" o "nosotros"). La presente Política de Privacidad describe de manera transparente qué información se recopila, cómo se utiliza y cómo se protege cuando descargas, instalas y utilizas SuperNewsApp en dispositivos Android.

La Aplicación está diseñada bajo el principio fundamental de respeto a la privacidad del usuario y minimización de datos: **no solicitamos registro de cuenta, no creamos perfiles de usuario, no recopilamos identificadores personales ni vendemos información a terceros.**

---

## 1. Identidad y Datos de Contacto del Desarrollador

En cumplimiento con los estándares y directrices para desarrolladores de Google Play Store y las políticas de aplicaciones de noticias:

- **Desarrollador / Responsable:** FeryaelJustice (Fernando González Serrano)
- **Correo electrónico de contacto:** [fgonzalezserrano10@gmail.com](mailto:fgonzalezserrano10@gmail.com)
- **Teléfono de contacto:** +34 609 28 38 51
- **Repositorio oficial del proyecto:** [https://github.com/FeryaelJustice/SuperNewsApp](https://github.com/FeryaelJustice/SuperNewsApp)
- **Página informativa de contacto:** [https://feryaeljustice.github.io/SuperNewsApp/contact.html](https://feryaeljustice.github.io/SuperNewsApp/contact.html)

---

## 2. Naturaleza de la Aplicación (Agregador de Noticias)

SuperNewsApp opera estrictamente como una aplicación móvil de agregación y lectura de noticias públicas de actualidad:

- Los contenidos (titulares, resúmenes, autores y publicaciones) se obtienen a través de la interfaz de programación de aplicaciones de **NewsAPI** ([newsapi.org](https://newsapi.org/)).
- En cada noticia visualizada dentro de la Aplicación se proporcionan la atribución explícita del medio de origen (publisher), el autor (cuando está disponible) y el enlace original para acceder al artículo completo en la web oficial del proveedor de contenidos.
- La Aplicación no produce noticias propias de redacción ni manipula la autoría ni el sentido original de las publicaciones agregadas.

---

## 3. Datos que Recopilamos y Procesamos

### 3.1. Datos Personales
- **No recopilamos información personal identificable (PII).** No solicitamos tu nombre, dirección, documento de identidad, lista de contactos, ubicación geográfica precisa ni credenciales bancarias.
- **Sin cuentas de usuario:** No existe ningún sistema de inicio de sesión ni creación de perfiles en la nube.

### 3.2. Datos Almacenados Localmente en el Dispositivo
Para el correcto funcionamiento funcional de la Aplicación, ciertos datos se procesan y almacenan **única y exclusivamente de manera local** en el almacenamiento privado de tu dispositivo:

1. **Marcadores y Artículos Guardados (Bookmarks):** Si decides guardar una noticia como favorita, los datos del artículo (título, descripción, URL, autor, medio de origen y fecha) se almacenan en una base de datos local SQLite mediante la biblioteca **Room**. Esta información no sale jamás de tu dispositivo ni se sincroniza con servidores externos propios.
2. **Preferencias de Configuración:** Utilizamos **Jetpack DataStore (Preferences)** para recordar estados básicos de la aplicación (por ejemplo, si el usuario ya ha completado la pantalla de bienvenida o onboarding).
3. **Caché Temporal de Imágenes:** Se utiliza la biblioteca **Coil** para almacenar temporalmente en caché las imágenes de portada de las noticias visualizadas, optimizando el consumo de datos de red y acelerando la navegación.

*Nota:* Puedes eliminar estos datos locales en cualquier momento borrando el almacenamiento o desinstalando la aplicación desde los ajustes de Android.

### 3.3. Permisos del Sistema Operativo Android
SuperNewsApp únicamente solicita los permisos estrictamente requeridos para su funcionalidad:

- `android.permission.INTERNET`: Permiso de red estándar para consultar las noticias en tiempo real a través de NewsAPI, descargar imágenes y opcionalmente traducir fragmentos mediante DeepL.

La aplicación **no** requiere ni solicita permisos sensibles como cámara, micrófono, almacenamiento compartido externo, contactos, llamadas, SMS ni localización por GPS.

---

## 4. Servicios y Proveedores de Terceros

La Aplicación se comunica con determinados servicios de terceros estrictamente necesarios para ofrecer la funcionalidad solicitada:

1. **NewsAPI (newsapi.org):**
   - Se utiliza para consultar titulares y noticias por categorías o términos de búsqueda.
   - Las consultas transmiten únicamente los parámetros de búsqueda o categoría seleccionados. Consulta la política de NewsAPI en [https://newsapi.org/privacy](https://newsapi.org/privacy).
2. **DeepL API (deepl.com):**
   - Utilizado opcionalmente para brindar soporte de traducción de textos a los usuarios.
   - Consulta la política de DeepL en [https://www.deepl.com/privacy](https://www.deepl.com/privacy).
3. **Google Play Services (Integrity API):**
   - La aplicación puede utilizar bibliotecas estándar de Play Integrity para garantizar la autenticidad e integridad del binario distribuido en Play Store y proteger contra alteraciones maliciosas.

SuperNewsApp **no** integra redes de publicidad (como Google AdMob, Unity Ads o Facebook Audience Network) ni herramientas de seguimiento comercial invasivas.

---

## 5. Enlaces a Sitios Web Externos

Al pulsar sobre el botón de lectura completa ("Leer en la web original" / "Read full story"), la Aplicación abrirá el navegador predeterminado del sistema o una pestaña personalizada hacia el sitio web oficial del editor de la noticia.

Nosotros no controlamos ni somos responsables de las prácticas de privacidad, cookies o contenidos de dichos sitios web externos. Te recomendamos revisar la política de privacidad de cada medio o sitio web que visites.

---

## 6. Comunicaciones Iniciadas por el Usuario (Soporte y Contacto)

Si utilizas la sección de contacto o decides enviarnos un mensaje de sugerencia/soporte:
- La aplicación abrirá tu cliente de correo electrónico preferido (mediante un Intent de Android) dirigido a `fgonzalezserrano10@gmail.com`.
- La información remitida (tu dirección de correo y el contenido del mensaje) será utilizada exclusivamente para responder a tu consulta o dar seguimiento a incidencias técnicas reportadas.
- Estos datos nunca se incorporarán a listas de mercadotecnia ni se cederán a terceros.

---

## 7. Privacidad de Menores (COPPA y directrices de Google Play)

SuperNewsApp está dirigida al público general interesado en noticias informativas y no está orientada a menores de 13 años. No recopilamos conscientemente ningún tipo de dato personal de menores de edad. Si un padre, madre o tutor legal toma conocimiento de que un menor ha remitido información personal vía correo de soporte, puede contactarnos para su inmediata supresión.

---

## 8. Seguridad de los Datos

Nos comprometemos a garantizar la seguridad de la aplicación:
- Todas las comunicaciones con las API externas se realizan obligatoriamente mediante protocolos cifrados seguros **HTTPS (TLS)**.
- Los datos locales se conservan dentro del espacio aislado de aplicación (App Sandbox) gestionado de forma nativa por el sistema operativo Android.

---

## 9. Derechos del Usuario (ARCO / RGPD)

Puesto que SuperNewsApp no almacena datos personales en servidores remotos ni mantiene registros de usuarios, el ejercicio de control sobre los datos locales está enteramente en tus manos:
- Puedes eliminar todos los artículos guardados y preferencias en cualquier instante eliminando los datos de la aplicación desde **Ajustes > Aplicaciones > SuperNews > Almacenamiento > Borrar datos**, o simplemente desinstalando la aplicación.
- Si tienes preguntas sobre privacidad o deseas realizar una consulta, puedes escribir directamente a [fgonzalezserrano10@gmail.com](mailto:fgonzalezserrano10@gmail.com).

---

## 10. Modificaciones a esta Política de Privacidad

Podemos actualizar esta Política de Privacidad periódicamente para reflejar cambios en la aplicación, nuevas versiones publicadas en Google Play Store o actualizaciones regulatorias. Cualquier modificación se publicará en este mismo archivo dentro del repositorio oficial de GitHub indicando la fecha de última revisión.

---

## 11. Enlace Oficial para Google Play Store

El enlace permanente y directo a este documento para su inclusión en la ficha de Play Console y dentro de la propia aplicación es:

- **Markdown directo en el repositorio:**  
  `https://github.com/FeryaelJustice/SuperNewsApp/blob/master/PRIVACY_POLICY.md`

- **Visualización web RAW:**  
  `https://raw.githubusercontent.com/FeryaelJustice/SuperNewsApp/master/PRIVACY_POLICY.md`
