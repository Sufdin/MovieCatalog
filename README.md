
# Movie Catalog - Каталог фильмов

## Описание
Android-приложение для ведения личного каталога фильмов и сериалов. Позволяет добавлять фильмы, отслеживать статус просмотра, оценивать и хранить информацию о просмотренных картинах.

## Автор
**ФИО:** [Суфиянов Динислам Русланович]  
**Группа:** [ТОП-102Б]  
**Дата:** Май 2026

## Основные функции
-  Добавление новых фильмов
-  Просмотр списка всех фильмов
-  Подробный просмотр информации о фильме
-  Редактирование записей
-  Удаление фильмов с подтверждением
-  Сохранение данных в локальной базе SQLite
-  Валидация пользовательского ввода

## Скриншоты

### Главный экран
![Main Screen](screenshots/main.png)

### Добавление фильма
![Add Movie](screenshots/add.png)

### Просмотр и редактирование
![Details](screenshots/details.png)

*(Замените пути на реальные скриншоты)*

## Используемые технологии
- **Язык:** Java
- **UI:** XML Layouts
- **Хранение данных:** SQLite
- **Компоненты:** RecyclerView, CardView, FloatingActionButton
- **Минимальный SDK:** API 24 (Android 7.0)

## Структура проекта
```
app/src/main/java/com/yourname/moviecatalog/
├── adapter/
│   └── MovieAdapter.java          # Адаптер для RecyclerView
├── database/
│   ├── MovieDatabaseHelper.java   # Помощник SQLite
│   └── MovieRepository.java       # CRUD операции
├── model/
│   └── Movie.java                 # Модель данных
└── ui/
    ├── MainActivity.java          # Главный экран
    ├── AddMovieActivity.java      # Экран добавления
    └── MovieDetailsActivity.java  # Экран деталей
```

## Инструкция по запуску

### Требования
- Android Studio Arctic Fox или новее
- JDK 11 или выше
- Эмулятор Android API 24+ или физическое устройство
