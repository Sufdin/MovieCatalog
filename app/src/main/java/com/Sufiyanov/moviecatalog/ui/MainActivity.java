package com.Sufiyanov.moviecatalog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.Sufiyanov.moviecatalog.R;
import com.Sufiyanov.moviecatalog.adapter.MovieAdapter;
import com.Sufiyanov.moviecatalog.database.MovieRepository;
import com.Sufiyanov.moviecatalog.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Инициализация элементов
        repository = new MovieRepository(this);
        recyclerView = findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создаем пустой список для адаптера
        adapter = new MovieAdapter(List.of(), this);
        recyclerView.setAdapter(adapter);

        // 2. Кнопка добавления (FAB)
        FloatingActionButton fabAdd = findViewById(R.id.fab_add_movie);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на экран добавления
                Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
                startActivity(intent);
            }
        });
    }

    // Этот метод вызывается каждый раз, когда Activity становится видимым
    @Override
    protected void onResume() {
        super.onResume();
        loadMovies(); // Обновляем список
    }

    // Метод загрузки данных из БД
    private void loadMovies() {
        List<Movie> movies = repository.getAllMovies();
        adapter.setMovies(movies);

        // Если список пуст, можно показать сообщение (опционально)
        if (movies.isEmpty()) {
            Toast.makeText(this, "Список пуст. Добавьте фильм!", Toast.LENGTH_SHORT).show();
        }
    }

    // Реализация клика по элементу списка
    @Override
    public void onItemClick(Movie movie) {
        // Переход на экран деталей, передаем ID фильма
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("MOVIE_ID", movie.getId());
        startActivity(intent);
    }
}