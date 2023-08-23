package domain.usecase;

import android.content.Context;
import android.os.Handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import data.PokemonDetailApiService;
import data.database.AppDatabase;
import data.database.entity.PokemonDetailEntity;
import data.model.PokemonDetailResponse;
import domain.model.PokemonDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailInteractorImpl implements PokemonDetailInteractor {
    private PokemonDetailApiService apiService;
    private AppDatabase appDatabase;
    private Executor executor;
    private Handler handler;

    public PokemonDetailInteractorImpl(PokemonDetailApiService apiService, Context context) {
        this.apiService = apiService;
        this.appDatabase = AppDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler();
    }

    @Override
    public void fetchPokemonDetail(int pokemonId, PokemonDetailCallback callback) {
        executor.execute(() -> {
            PokemonDetailEntity cachedDetailEntity = appDatabase.pokemonDetailDao().getPokemonDetail(pokemonId);

            if (cachedDetailEntity != null) {
                PokemonDetail cachedDetail = cachedDetailEntity.toDomainModel();
                handler.post(() -> callback.onSuccess(cachedDetail));
            } else {
                Call<PokemonDetailResponse> call = apiService.getPokemonDetail(pokemonId);
                call.enqueue(new Callback<PokemonDetailResponse>() {
                    @Override
                    public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                        if (response.isSuccessful()) {
                            PokemonDetailResponse detailResponse = response.body();

                            PokemonDetail pokemonDetail = new PokemonDetail();
                            pokemonDetail.setName(detailResponse.getName());
                            pokemonDetail.setHeight(detailResponse.getHeight());
                            pokemonDetail.setWeight(detailResponse.getWeight());
                            pokemonDetail.setTypes(detailResponse.getTypes());
                            pokemonDetail.setFrontDefault(detailResponse.getSprites().getFrontDefaultUrl());

                            executor.execute(() -> {
                                PokemonDetailEntity detailEntity = PokemonDetailEntity.fromDomainModel(pokemonDetail,pokemonId);
                                appDatabase.pokemonDetailDao().insertPokemonDetail(detailEntity);
                                handler.post(() -> callback.onSuccess(pokemonDetail));
                            });
                        } else {
                            handler.post(() -> callback.onFailure(new Exception("Response not successful")));
                        }
                    }

                    @Override
                    public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                        handler.post(() -> callback.onFailure(t));
                    }
                });
            }
        });
    }
}
