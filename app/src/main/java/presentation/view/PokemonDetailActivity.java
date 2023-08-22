package presentation.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemonreview.R;

import data.RetrofitClient;
import domain.model.PokemonDetail;
import domain.usecase.PokemonDetailCallback;
import domain.usecase.PokemonDetailInteractor;
import domain.usecase.PokemonDetailInteractorImpl;


public class PokemonDetailActivity extends AppCompatActivity {
        private PokemonDetailInteractor detailInteractor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pokemon_detail);

            detailInteractor = new PokemonDetailInteractorImpl(RetrofitClient.createDetail());


            String pokemonUrl = getIntent().getStringExtra("pokemon_id");

            String[] urlParts = pokemonUrl.split("/");
            String pokemonIdString = urlParts[urlParts.length - 1];

            int pokemonId= Integer.parseInt(pokemonIdString);

            loadPokemonDetail(pokemonId);
        }

    private void loadPokemonDetail(int pokemonId) {
        detailInteractor.fetchPokemonDetail(pokemonId, new PokemonDetailCallback() {
            @Override
            public void onSuccess(PokemonDetail pokemonDetail) {


                TextView nameTextView = findViewById(R.id.nameTextView);
                TextView heightTextView = findViewById(R.id.heightTextView);
                TextView weightTextView = findViewById(R.id.weightTextView);
                TextView typesTextView = findViewById(R.id.typesTextView);

                nameTextView.setText(pokemonDetail.getName());
                heightTextView.setText("Height: " + pokemonDetail.getHeight() + " m");
                weightTextView.setText("Weight: " + pokemonDetail.getWeight() + " kg");
                String formattedTypes = pokemonDetail.getFormattedTypes();
                typesTextView.setText("Types: " + formattedTypes);



            }

            @Override
            public void onFailure(Throwable throwable) {
                // Обработка ошибки
            }
        });
    }

}