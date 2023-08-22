package domain.usecase;
import java.util.List;

import data.PokemonApiService;
import data.model.PokemonListResponse;
import domain.model.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonInteractorImpl implements PokemonInteractor {
    private PokemonApiService apiService;

    public PokemonInteractorImpl(PokemonApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void fetchPokemonList(int offset, int limit, PokemonFetchCallback callback) {
        Call<PokemonListResponse> call = apiService.getPokemonList(offset, limit);

        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    List<Pokemon> newPokemonList = response.body().getResults();
                    callback.onSuccess(newPokemonList);
                } else {
                    callback.onFailure(new Exception("Response not successful")); // Handle error
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                callback.onFailure(t); // Handle failure
            }
        });
    }
}

