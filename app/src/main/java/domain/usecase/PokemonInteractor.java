package domain.usecase;

public interface PokemonInteractor {
    void fetchPokemonList(int offset, int limit, PokemonFetchCallback callback);

}