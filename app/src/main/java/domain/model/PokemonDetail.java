package domain.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetail {
    private String name;
    private double height;
    private double weight;
    private List<PokemonType> types;

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

    public void setTypes(List<PokemonType> types) {
        this.types = types;
    }

    public PokemonDetail(String name, double height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }
    public class PokemonType {
        private int slot;
        private Type type;

        public Type getType() {
            return type;
        }
    }

    public class Type {
        private String name;
        private String url;

        public String getName() {
            return name;
        }
    }

}
