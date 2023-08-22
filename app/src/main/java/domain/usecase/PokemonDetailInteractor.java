package domain.usecase;

public interface PokemonDetailInteractor {
    void fetchPokemonDetail(int pokemonId, PokemonDetailCallback callback);
}
