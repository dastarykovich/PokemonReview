package presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemonreview.R;
import com.squareup.picasso.Picasso;

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

            setupBackButton();
            loadPokemonDetailFromIntent();
        }
    private void setupBackButton() {
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadPokemonDetailFromIntent() {
        String pokemonUrl = getIntent().getStringExtra("pokemon_id");
        String[] urlParts = pokemonUrl.split("/");
        String pokemonIdString = urlParts[urlParts.length - 1];
        int pokemonId = Integer.parseInt(pokemonIdString);
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
                ImageView imageView = findViewById(R.id.imageView);

                nameTextView.setText(pokemonDetail.getName());
                heightTextView.setText("Height: " + pokemonDetail.getHeight() + " m");
                weightTextView.setText("Weight: " + pokemonDetail.getWeight() + " kg");
                String formattedTypes = pokemonDetail.getFormattedTypes();
                typesTextView.setText("Types: " + formattedTypes);
                Picasso.get().load(pokemonDetail.getFrontDefault()).into(imageView);



            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("PokemonDetailActivity", "Error loading Pokemon details", throwable);
                Toast.makeText(PokemonDetailActivity.this, "Error loading Pokemon details", Toast.LENGTH_SHORT).show();


            }
        });
    }

}