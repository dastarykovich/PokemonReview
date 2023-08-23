package domain.usecase;

import data.PokemonApiService;
import data.PokemonDetailApiService;
import data.model.PokemonDetailResponse;
import domain.model.PokemonDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailInteractorImpl implements PokemonDetailInteractor {
    private PokemonDetailApiService apiService;

    public PokemonDetailInteractorImpl(PokemonDetailApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void fetchPokemonDetail(int pokemonId, PokemonDetailCallback callback) {
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

                    callback.onSuccess(pokemonDetail);
                } else {
                    callback.onFailure(new Exception("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}