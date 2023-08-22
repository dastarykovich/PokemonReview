package data;

import data.model.PokemonDetailResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonDetailApiService {
    @GET("pokemon/{id}/")
    Call<PokemonDetailResponse> getPokemonDetail(@Path("id") int pokemonId);
}