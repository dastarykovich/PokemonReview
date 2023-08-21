package data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonApiService {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);
}
