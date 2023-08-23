package data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import data.database.dao.PokemonDao;
import data.database.entity.PokemonEntity;

@Database(entities = {PokemonEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract PokemonDao pokemonDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "pokemon_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
