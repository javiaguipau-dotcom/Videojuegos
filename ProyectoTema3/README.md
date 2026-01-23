# Proyecto Tema 3 - Gestión de Videojuegos

## 📱 Descripción

**ProyectoTema3** es una aplicación Android desarrollada en **Kotlin** que permite gestionar y visualizar un catálogo de videojuegos. La aplicación muestra una lista de videojuegos con información detallada de cada título, incluyendo consola, año de lanzamiento e imagen de portada.

## ✨ Características

- 📋 **Visualización de Videojuegos**: Interfaz con RecyclerView para mostrar una lista dinámica de videojuegos
- 🗑️ **Eliminar Videojuegos**: Posibilidad de eliminar títulos de la lista con un gesto simple
- 🎮 **Datos de Ejemplo**: Incluye videojuegos populares precargados (The Legend of Zelda, God of War, etc.)
- 📸 **Imágenes de Portadas**: Carga de imágenes desde URLs remotas
- 🎯 **Interfaz Intuitiva**: Diseño Material Design con FloatingActionButton

## 🛠️ Tecnologías

- **Lenguaje**: Kotlin
- **API Mínima**: Android 7.0 (SDK 24)
- **API Objetivo**: Android 15 (SDK 36)
- **Framework**: AndroidX
- **Componentes principales**:
  - RecyclerView
  - Material Design
  - FloatingActionButton
  - LinearLayoutManager

## 📂 Estructura del Proyecto

```
ProyectoTema3/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/proyectotema3/
│   │   │   │   ├── MainActivity.kt          # Actividad principal
│   │   │   │   ├── adapter/                 # Adaptadores para RecyclerView
│   │   │   │   ├── controler/               # Controladores (lógica de negocio)
│   │   │   │   ├── dao/                     # Data Access Objects
│   │   │   │   ├── interfaces/              # Interfaces del proyecto
│   │   │   │   ├── models/                  # Clases de datos (Videojuego)
│   │   │   │   └── object_models/           # Modelos adicionales
│   │   │   ├── res/                         # Recursos (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                     # Pruebas instrumentadas
│   │   └── test/                            # Pruebas unitarias
│   └── build.gradle.kts                     # Configuración de Gradle
├── gradle/                                  # Configuración de Gradle
├── build.gradle.kts                         # Build del proyecto
├── settings.gradle.kts                      # Configuración del proyecto
└── README.md                                # Este archivo
```

## 🎬 Clases Principales

### MainActivity.kt
- Actividad principal de la aplicación
- Gestiona el RecyclerView para mostrar la lista de videojuegos
- Implementa el controlador de videojuegos
- Maneja eventos del FloatingActionButton

### Videojuego (models/)
Clase de datos que representa un videojuego:
```kotlin
data class Videojuego(
    val id: Int,
    val titulo: String,
    val consola: String,
    val año: Int,
    val imagen: String
)
```

### AdapterVideojuegos (adapter/)
- Adaptador personalizado para RecyclerView
- Gestiona la visualización de cada videojuego en la lista
- Implementa callbacks para eliminar videojuegos

### VideojuegoController (controler/)
- Controlador que gestiona la lógica de negocio
- Métodos: `insertar()`, `borrar()`, `getListado()`

## 🚀 Instalación y Uso

### Requisitos
- Android Studio Koala o superior
- JDK 11 o superior
- Android SDK 24 o superior

### Pasos para ejecutar

1. **Clonar o abrir el proyecto**
   ```bash
   cd ProyectoTema3
   ```

2. **Sincronizar Gradle**
   - Abre el proyecto en Android Studio
   - Gradle se sincronizará automáticamente

3. **Ejecutar la aplicación**
   - Conecta un dispositivo Android o abre un emulador
   - Presiona el botón "Run" o usa: `./gradlew installDebug`

## 📝 Funcionalidades Implementadas

✅ Mostrar lista de videojuegos  
✅ Cargar imágenes de portadas  
✅ Eliminar videojuegos de la lista  
✅ Interfaz Material Design  
✅ RecyclerView optimizado  

## 🔄 Funcionalidades Futuras

⏳ Agregar nuevos videojuegos  
⏳ Editar información de videojuegos  
⏳ Búsqueda y filtrado  
⏳ Persistencia de datos (Base de datos)  
⏳ Detalles expandidos de cada videojuego  
⏳ Sincronización con API remota  

## 📦 Dependencias

```gradle-kotlin-dsl
// AndroidX
androidx.core.ktx
androidx.appcompat
androidx.activity
androidx.constraintlayout

// Material Design
material

// Testing
androidx.test.runner.AndroidJUnitRunner
```

## 🎮 Datos de Ejemplo

La aplicación viene preacargada con:
- **The Legend of Zelda: Breath of the Wild** (Nintendo Switch, 2017)
- **God of War Ragnarök** (PlayStation 5, 2022)

## 📄 Licencia

Este proyecto es educativo y fue desarrollado como parte del Tema 3 del curso.

## 👤 Autor

Desarrollado como proyecto de estudio en Android.

---

**Versión**: 1.0  
**Compilación**: 36  
**Última actualización**: Enero 2026
