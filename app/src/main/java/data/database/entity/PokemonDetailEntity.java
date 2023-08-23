package data.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import domain.model.PokemonDetail;

@Entity(tableName = "pokemon_detail")
public class PokemonDetailEntity {
    @PrimaryKey
    private int pokemonId;

    private String name;
    private double height;
    private double weight;
    private String types;
    private String frontDefault;

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    // Дополнительные методы, если необходимо

    public PokemonDetail toDomainModel() {
        PokemonDetail pokemonDetail = new PokemonDetail();
        pokemonDetail.setName(name);
        pokemonDetail.setHeight(height);
        pokemonDetail.setWeight(weight);
        pokemonDetail.setFrontDefault(frontDefault);

        List<PokemonDetail.PokemonType> typeList = new ArrayList<>();

        pokemonDetail.setTypes(typeList);

        return pokemonDetail;
    }

    public static PokemonDetailEntity fromDomainModel(PokemonDetail pokemonDetail,int id) {
        PokemonDetailEntity entity = new PokemonDetailEntity();
        entity.setPokemonId(id);
        entity.setName(pokemonDetail.getName());
        entity.setHeight(pokemonDetail.getHeight());
        entity.setWeight(pokemonDetail.getWeight());
        entity.setFrontDefault(pokemonDetail.getFrontDefault());

        return entity;
    }
}
