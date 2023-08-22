package presentation.presenter;

import domain.model.Pokemon;

public interface PokemonPresenter {
    void fetchPokemonList();
    void onPokemonClicked(Pokemon pokemon);
}
