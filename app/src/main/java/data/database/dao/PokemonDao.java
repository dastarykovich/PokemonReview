package data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import data.database.entity.PokemonEntity;

@Dao
public interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemon(PokemonEntity pokemon);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPokemon(List<PokemonEntity> pokemonEntities);

    @Query("SELECT * FROM pokemon")
    List<PokemonEntity> getAllPokemon();
}
