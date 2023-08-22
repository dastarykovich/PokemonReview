package data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailRetrofitClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public static PokemonDetailApiService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(PokemonDetailApiService.class);
    }
}
