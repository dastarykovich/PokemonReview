package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.pokemonreview.R;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import data.PokemonApiService;
import data.PokemonListResponse;
import data.RetrofitClient;
import domain.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PokemonAdapter();
        recyclerView.setAdapter(adapter);

        fetchPokemonList();
    }

    private void fetchPokemonList() {
        PokemonApiService apiService = RetrofitClient.create();
        Call<PokemonListResponse> call = apiService.getPokemonList();

        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    adapter.setData(pokemonList);
                }
                else {
                    Log.e("PokemonActivity", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e("PokemonActivity", "API call failed", t);
            }
        });
    }
}