package com.Sufiyanov.moviecatalog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Sufiyanov.moviecatalog.R;
import com.Sufiyanov.moviecatalog.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnItemClickListener listener;

    // Интерфейс для обработки кликов
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Связываем адаптер с нашим XML файлом карточки
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);
        // Заполняем элементы карточки данными
        holder.textViewTitle.setText(currentMovie.getTitle());
        holder.textViewGenre.setText(currentMovie.getGenre() + " • " + currentMovie.getYear());
        holder.textViewRating.setText(String.valueOf(currentMovie.getRating()));

        // При клике передаем объект Movie в слушатель
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentMovie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Метод для обновления списка (когда добавили новый фильм)
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    // Класс ViewHolder — хранит ссылки на элементы XML
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewGenre;
        TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_movie_title);
            textViewGenre = itemView.findViewById(R.id.tv_movie_genre);
            textViewRating = itemView.findViewById(R.id.tv_movie_rating);
        }
    }
}