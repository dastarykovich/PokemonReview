package domain.usecase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import data.PokemonApiService;
import data.database.AppDatabase;
import data.database.entity.PokemonEntity;
import data.model.PokemonListResponse;
import domain.model.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonInteractorImpl implements PokemonInteractor {
    private PokemonApiService apiService;
    private AppDatabase appDatabase;


    public PokemonInteractorImpl(PokemonApiService apiService,/*fff*/Context context) {
        this.apiService = apiService;
        ///
        this.appDatabase = AppDatabase.getInstance(context);


        ///
    }

    Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void fetchPokemonList(int offset, int limit, PokemonFetchCallback callback) {
        Call<PokemonListResponse> call = apiService.getPokemonList(offset, limit);


        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    List<Pokemon> newPokemonList = response.body().getResults();


                    executor.execute(() -> {
                        List<PokemonEntity> pokemonEntities = new ArrayList<>();
                        for (Pokemon pokemon : newPokemonList) {
                            pokemonEntities.add(PokemonEntity.fromDomainModel(pokemon));
                        }
                        appDatabase.pokemonDao().insertAllPokemon(pokemonEntities);

                        callback.onSuccess(newPokemonList);
                    });
                } else {
                    callback.onFailure(new Exception("Response not successful"));
                }
            }


            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}

