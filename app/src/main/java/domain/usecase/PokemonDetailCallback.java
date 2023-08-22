package domain.usecase;

import domain.model.PokemonDetail;

public interface PokemonDetailCallback {
    void onSuccess(PokemonDetail pokemonDetail);
    void onFailure(Throwable throwable);
}
