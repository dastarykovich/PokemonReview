package data.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import domain.model.Pokemon;

@Entity(tableName = "pokemon")
public class PokemonEntity {
    @PrimaryKey
    public int id;

    public String name;
    public String url;

    public static PokemonEntity fromDomainModel(Pokemon pokemon,int count) {
        PokemonEntity entity = new PokemonEntity();

        entity.id=count;
        entity.name = pokemon.getName();
        entity.url = pokemon.getUrl();
        return entity;
    }

    public Pokemon toDomainModel() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
        pokemon.setUrl(url);
        return pokemon;
    }
}
