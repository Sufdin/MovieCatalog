package com.Sufiyanov.moviecatalog.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.Sufiyanov.moviecatalog.R;
import com.Sufiyanov.moviecatalog.database.MovieRepository;
import com.Sufiyanov.moviecatalog.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private EditText etTitle, etYear, etGenre, etRating, etDescription;
    private RadioGroup rgStatus;
    private Button btnSave, btnDelete;
    private MovieRepository repository;
    private Movie currentMovie;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initViews();
        repository = new MovieRepository(this);

        // 1. Получаем ID фильма из Intent (переданного из MainActivity или Adapter)
        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        if (movieId == -1) {
            Toast.makeText(this, "Ошибка: ID фильма не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 2. Загружаем данные из БД
        loadMovieData();

        // 3. Обработчики кнопок
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMovie();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void initViews() {
        etTitle = findViewById(R.id.et_title_detail);
        etYear = findViewById(R.id.et_year_detail);
        etGenre = findViewById(R.id.et_genre_detail);
        etRating = findViewById(R.id.et_rating_detail);
        etDescription = findViewById(R.id.et_description_detail);
        rgStatus = findViewById(R.id.radio_group_status_detail);
        btnSave = findViewById(R.id.btn_save_detail);
        btnDelete = findViewById(R.id.btn_delete);
    }

    /**
     * Загрузка данных фильма из БД и заполнение полей
     */
    private void loadMovieData() {
        currentMovie = repository.getMovieById(movieId);
        if (currentMovie == null) {
            Toast.makeText(this, "Фильм не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Заполняем EditText
        etTitle.setText(currentMovie.getTitle());
        etYear.setText(String.valueOf(currentMovie.getYear()));
        etGenre.setText(currentMovie.getGenre());
        etRating.setText(String.valueOf(currentMovie.getRating()));
        etDescription.setText(currentMovie.getDescription());

        // Устанавливаем выбранный RadioButton в зависимости от статуса
        String status = currentMovie.getStatus();
        if (status.equals("В процессе")) {
            rgStatus.check(R.id.rb_watching_detail);
        } else if (status.equals("Просмотрено")) {
            rgStatus.check(R.id.rb_watched_detail);
        } else {
            rgStatus.check(R.id.rb_planned_detail); // По умолчанию
        }
    }

    /**
     * Валидация и сохранение изменений в БД
     */
    private void updateMovie() {
        String title = etTitle.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // --- ВАЛИДАЦИЯ (аналогично экрану добавления) ---
        if (title.isEmpty()) {
            etTitle.setError("Введите название");
            return;
        }
        if (yearStr.isEmpty()) {
            etYear.setError("Введите год");
            return;
        }
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            etYear.setError("Год должен быть числом");
            return;
        }
        if (genre.isEmpty()) {
            etGenre.setError("Укажите жанр");
            return;
        }

        float rating = 0f;
        if (!ratingStr.isEmpty()) {
            try {
                rating = Float.parseFloat(ratingStr);
                if (rating < 0 || rating > 10) {
                    etRating.setError("Рейтинг от 0 до 10");
                    return;
                }
            } catch (NumberFormatException e) {
                etRating.setError("Неверный формат");
                return;
            }
        }

        // Определяем новый статус
        String newStatus = "Планирую";
        int checkedId = rgStatus.getCheckedRadioButtonId();
        if (checkedId == R.id.rb_watching_detail) newStatus = "В процессе";
        else if (checkedId == R.id.rb_watched_detail) newStatus = "Просмотрено";

        // Создаем обновленный объект с тем же ID
        Movie updatedMovie = new Movie(
                movieId, title, year, genre, rating, description, newStatus
        );

        // Сохраняем в БД
        int rowsUpdated = repository.updateMovie(updatedMovie);
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
            finish(); // Возврат на главный экран -> список обновится
        } else {
            Toast.makeText(this, "Ошибка обновления", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Диалог подтверждения удаления
     */
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Удаление фильма")
                .setMessage("Вы уверены, что хотите удалить \"" + currentMovie.getTitle() + "\"?")
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.deleteMovie(movieId);
                        Toast.makeText(MovieDetailsActivity.this, "Фильм удален", Toast.LENGTH_SHORT).show();
                        finish(); // Возврат на главный экран
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}