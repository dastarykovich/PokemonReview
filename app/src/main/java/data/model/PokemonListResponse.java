package data.model;

import java.util.List;

import domain.model.Pokemon;

public class PokemonListResponse {
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }
}
