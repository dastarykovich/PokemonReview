package data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonApiService {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList();
}