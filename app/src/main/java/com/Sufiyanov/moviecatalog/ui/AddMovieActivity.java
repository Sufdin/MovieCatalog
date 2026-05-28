package com.Sufiyanov.moviecatalog.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Sufiyanov.moviecatalog.R;
import com.Sufiyanov.moviecatalog.database.MovieRepository;
import com.Sufiyanov.moviecatalog.model.Movie;

public class AddMovieActivity extends AppCompatActivity {

    private EditText etTitle, etYear, etGenre, etRating, etDescription;
    private RadioGroup rgStatus;
    private Button btnSave;
    private MovieRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // 1. Привязка элементов интерфейса
        initViews();

        // 2. Инициализация репозитория БД
        repository = new MovieRepository(this);

        // 3. Обработчик кнопки "Сохранить"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovie();
            }
        });
    }

    private void initViews() {
        etTitle = findViewById(R.id.et_title);
        etYear = findViewById(R.id.et_year);
        etGenre = findViewById(R.id.et_genre);
        etRating = findViewById(R.id.et_rating);
        etDescription = findViewById(R.id.et_description);
        rgStatus = findViewById(R.id.radio_group_status);
        btnSave = findViewById(R.id.btn_save);
    }

    /**
     * Метод сбора данных, валидации и сохранения в БД
     */
    private void saveMovie() {
        // Получаем текст из полей
        String title = etTitle.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // --- ВАЛИДАЦИЯ (Обязательное требование из задания) ---

        // 1. Название не должно быть пустым
        if (title.isEmpty()) {
            etTitle.setError("Введите название фильма");
            etTitle.requestFocus();
            return;
        }

        // 2. Год должен быть заполнен и числом
        if (yearStr.isEmpty()) {
            etYear.setError("Введите год выпуска");
            etYear.requestFocus();
            return;
        }
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            etYear.setError("Год должен быть числом");
            return;
        }

        // 3. Жанр обязателен
        if (genre.isEmpty()) {
            etGenre.setError("Укажите жанр");
            etGenre.requestFocus();
            return;
        }

        // 4. Рейтинг опционален, но если введен — от 0 до 10
        float rating = 0f;
        if (!ratingStr.isEmpty()) {
            try {
                rating = Float.parseFloat(ratingStr);
                if (rating < 0 || rating > 10) {
                    etRating.setError("Рейтинг должен быть от 0 до 10");
                    return;
                }
            } catch (NumberFormatException e) {
                etRating.setError("Неверный формат рейтинга");
                return;
            }
        }

        // 5. Определение выбранного статуса
        String status = "Планирую"; // По умолчанию
        int selectedId = rgStatus.getCheckedRadioButtonId();
        if (selectedId == R.id.rb_watching) {
            status = "В процессе";
        } else if (selectedId == R.id.rb_watched) {
            status = "Просмотрено";
        }

        // --- СОЗДАНИЕ ОБЪЕКТА И СОХРАНЕНИЕ ---
        Movie newMovie = new Movie(title, year, genre, rating, description, status);

        // Вызываем метод репозитория. Возвращает ID новой записи или -1 при ошибке.
        long resultId = repository.addMovie(newMovie);

        if (resultId != -1) {
            Toast.makeText(this, "Фильм успешно добавлен!", Toast.LENGTH_SHORT).show();
            // finish() закрывает текущий экран и возвращает на MainActivity.
            // Список обновится автоматически благодаря onResume() в MainActivity.
            finish();
        } else {
            Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
        }
    }
}