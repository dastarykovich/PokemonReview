package domain.usecase;

import java.util.List;

import domain.model.Pokemon;

public interface PokemonFetchCallback {
    void onSuccess(List<Pokemon> newPokemonList);
    void onFailure(Throwable throwable);
}