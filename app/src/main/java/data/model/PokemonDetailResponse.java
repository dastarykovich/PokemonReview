package data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import domain.model.PokemonDetail;

public class PokemonDetailResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("height")
    private double height;

    @SerializedName("weight")
    private double weight;

    @SerializedName("types")
    private List<PokemonDetail.PokemonType> types;
    @SerializedName("sprites")
    private Sprites sprites;

    public String getName() {
        return name;
    }

    public List<PokemonDetail.PokemonType> getTypes() {
        return types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefaultUrl;

        public String getFrontDefaultUrl() {
            return frontDefaultUrl;
        }
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    // ... остальные геттеры для остальных полей ...
}