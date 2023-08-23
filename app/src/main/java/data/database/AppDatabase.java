package data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import data.database.dao.PokemonDao;
import data.database.dao.PokemonDetailDao;
import data.database.entity.PokemonDetailEntity;
import data.database.entity.PokemonEntity;

@Database(entities = {PokemonEntity.class, PokemonDetailEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract PokemonDao pokemonDao();
    public abstract PokemonDetailDao pokemonDetailDao();

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
