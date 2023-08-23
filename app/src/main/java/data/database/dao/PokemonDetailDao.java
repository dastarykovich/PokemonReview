package data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import data.database.entity.PokemonDetailEntity;

@Dao
public interface PokemonDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemonDetail(PokemonDetailEntity pokemonDetailEntity);

    @Query("SELECT * FROM pokemon_detail WHERE pokemonId = :pokemonId")
    PokemonDetailEntity getPokemonDetail(int pokemonId);
}
