# 🚗💨 BS Autos – Sistema Integral de Gestión para Concesionarias

[![Android](https://img.shields.io/badge/Android-Java-3ddc84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/)
[![SQLite](https://img.shields.io/badge/SQLite-3.0-blue?style=flat-square&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![UX Design](https://img.shields.io/badge/UX%2FUI-Optimizado-blueviolet?style=flat-square)]()
[![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)](LICENSE)

---

✨ ¿Qué es BS Autos?
BS Autos es una app Android profesional para la gestión integral de vehículos en concesionarias, pensada para modernizar la administración y agilizar el manejo de stock, ventas y control de autos en B&S Autos.

La desarrollé en Java usando Android Studio, aplicando los mejores patrones de diseño mobile y siguiendo buenas prácticas de seguridad, rendimiento y experiencia de usuario.

🚀 Características Destacadas
🔒 Login y registro de usuarios
Seguridad y validación completa (nunca se pierde un dato).

📝 CRUD total de autos
Alta, edición, consulta, eliminación… ¡gestioná tu stock de punta a punta!

🗄️ Base de datos local (SQLite)
Toda la información se guarda offline, rápida y segura.

⚡ Operaciones seguras y sin bloqueos
Las operaciones con la base de datos se ejecutan en hilos (threads) separados usando ExecutorService, evitando que la app se congele y asegurando máxima fluidez.

🔍 Vista de detalle
Marca, modelo, color, estado, valor, kilometraje y mucho más.

💸 Moneda local
Todos los valores en pesos argentinos (con campos validados).

🎨 Interfaz intuitiva y UX optimizada
Navegación fluida, diseño responsivo, íconos y feedback visual.

📲 Fácil de instalar, usar y expandir
Código limpio, modular y listo para agregar nuevas funciones.

```sh
🆕 Novedad Final: Buenas Prácticas en Base de Datos
Todas las operaciones de acceso y manipulación de la base de datos ahora se realizan en hilos (threads) independientes mediante el uso de ExecutorService.

Esto previene bloqueos en la interfaz y sigue las recomendaciones modernas de desarrollo Android.
```

🧑‍💻 ¿Cómo usar el proyecto?
Cloná o descargá el repositorio:

```sh
git clone https://github.com/brunomartins27/BSAutos_Mobile.git
```
Abrí el proyecto en Android Studio.

Ejecutá la app en un emulador o dispositivo físico.

Registrate como usuario y empezá a cargar autos en venta.


🔨 Tecnologías y herramientas
Java 8+

Android SDK

SQLite (base de datos local)

Material Design & UX Best Practices

Android Studio

Git & GitHub

🏁 Roadmap y posibles mejoras
Sincronización en la nube (Firebase o REST API)

Notificaciones push para novedades o alertas

Filtros avanzados y búsqueda inteligente de autos

Exportación de datos a PDF/Excel

Gestión de usuarios administradores

Integración con servicios de mapas o ubicación

📝 Créditos
Autor: Bruno Martins – Dev Jr
