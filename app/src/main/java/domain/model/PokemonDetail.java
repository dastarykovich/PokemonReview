package domain.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetail {
    private String name;
    private double height;
    private double weight;
    private List<PokemonType> types;
    private String frontDefault;

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
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

    public List<PokemonType> getTypes() {
        return types;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public PokemonDetail() {
    }
    public String getFormattedTypes() {
        List<String> typeNames = new ArrayList<>();
        for (PokemonType type : types) {
            typeNames.add(type.getType().getName());
        }
        return TextUtils.join(", ", typeNames);
    }
    public List<PokemonType> parseFormattedTypes(String formattedTypes) {
        List<PokemonType> parsedTypes = new ArrayList<>();
        String[] typeNames = formattedTypes.split(", ");
        for (String typeName : typeNames) {
            Type type = new Type();
            type.setName(typeName);
            PokemonType pokemonType = new PokemonType();
            pokemonType.setType(type);
            parsedTypes.add(pokemonType);
        }
        return parsedTypes;
    }

    public void setTypes(List<PokemonType> types) {
        this.types = types;
    }
    public class PokemonType {
        private int slot;
        private Type type;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type=type;
        }
    }

    public class Type {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String typeName) {
            this.name=typeName;
        }
    }

}
