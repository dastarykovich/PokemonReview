package presentation.view;

import java.util.List;

import domain.model.Pokemon;

public interface PokemonDetailView {
    void showPokemonList(List<Pokemon> pokemonList);
    void showPokemonDetail(String pokemonId);
    void showLoading(boolean isLoading);
    void showError(String errorMessage);
}