package domain.usecase;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

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
    private Executor executor;
    private Handler handler;

    public PokemonInteractorImpl(PokemonApiService apiService, Context context) {
        this.apiService = apiService;
        this.appDatabase = AppDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler();
    }

    @Override
    public void fetchPokemonList(int offset, int limit, PokemonFetchCallback callback) {
        executor.execute(() -> {
            List<PokemonEntity> cachedPokemonEntities = appDatabase.pokemonDao().getAllPokemon();

            if (!cachedPokemonEntities.isEmpty()&&cachedPokemonEntities.size() >offset) {
                List<Pokemon> cachedPokemonList = new ArrayList<>();
                for (PokemonEntity entity : cachedPokemonEntities) {
                    cachedPokemonList.add(entity.toDomainModel());
                }

                handler.post(() -> callback.onSuccess(cachedPokemonList));
            } else {
                Call<PokemonListResponse> call = apiService.getPokemonList(offset, limit);
                call.enqueue(new Callback<PokemonListResponse>() {
                    @Override
                    public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                        if (response.isSuccessful()) {
                            List<Pokemon> newPokemonList = response.body().getResults();
                            executor.execute(() -> {
                                List<PokemonEntity> pokemonEntities = new ArrayList<>();
                                int count =offset;
                                for (Pokemon pokemon : newPokemonList) {
                                    pokemonEntities.add(PokemonEntity.fromDomainModel(pokemon,count++));
                                }
                                appDatabase.pokemonDao().insertAllPokemon(pokemonEntities);
                                handler.post(() -> callback.onSuccess(newPokemonList));
                               });
                        } else {
                            handler.post(() -> callback.onFailure(new Exception("Response not successful")));
                        }
                    }

                    @Override
                    public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                        handler.post(() -> callback.onFailure(t));
                    }
                });
            }
        });
    }

}
