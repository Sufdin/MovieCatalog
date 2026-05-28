package com.Sufiyanov.moviecatalog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.Sufiyanov.moviecatalog.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private MovieDatabaseHelper dbHelper;

    public MovieRepository(Context context) {
        dbHelper = new MovieDatabaseHelper(context);
    }

    // --- CREATE (Добавление) ---
    public long addMovie(Movie movie) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseHelper.COLUMN_TITLE, movie.getTitle());
        values.put(MovieDatabaseHelper.COLUMN_YEAR, movie.getYear());
        values.put(MovieDatabaseHelper.COLUMN_GENRE, movie.getGenre());
        values.put(MovieDatabaseHelper.COLUMN_RATING, movie.getRating());
        values.put(MovieDatabaseHelper.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(MovieDatabaseHelper.COLUMN_STATUS, movie.getStatus());

        long newRowId = db.insert(MovieDatabaseHelper.TABLE_MOVIES, null, values);
        db.close();
        return newRowId;
    }

    // --- READ ALL (Получение всех записей) ---
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Сортировка по ID (новые внизу) или по названию
        String sortOrder = MovieDatabaseHelper.COLUMN_ID + " DESC";
        Cursor cursor = db.query(MovieDatabaseHelper.TABLE_MOVIES, null, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(
                        cursor.getInt(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_YEAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_GENRE)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_STATUS))
                );
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }

    // --- READ ONE (Получение одной записи по ID) ---
    public Movie getMovieById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Movie movie = null;

        Cursor cursor = db.query(
                MovieDatabaseHelper.TABLE_MOVIES,
                null,
                MovieDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            movie = new Movie(
                    cursor.getInt(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_TITLE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_YEAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_GENRE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_STATUS))
            );
            cursor.close();
        }
        db.close();
        return movie;
    }

    // --- UPDATE (Обновление) ---
    public int updateMovie(Movie movie) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseHelper.COLUMN_TITLE, movie.getTitle());
        values.put(MovieDatabaseHelper.COLUMN_YEAR, movie.getYear());
        values.put(MovieDatabaseHelper.COLUMN_GENRE, movie.getGenre());
        values.put(MovieDatabaseHelper.COLUMN_RATING, movie.getRating());
        values.put(MovieDatabaseHelper.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(MovieDatabaseHelper.COLUMN_STATUS, movie.getStatus());

        int rowsAffected = db.update(
                MovieDatabaseHelper.TABLE_MOVIES,
                values,
                MovieDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(movie.getId())}
        );
        db.close();
        return rowsAffected;
    }

    // --- DELETE (Удаление) ---
    public void deleteMovie(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MovieDatabaseHelper.TABLE_MOVIES, MovieDatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}