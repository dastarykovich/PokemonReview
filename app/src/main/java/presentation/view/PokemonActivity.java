package presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonreview.R;

import java.util.List;
import domain.model.Pokemon;
import presentation.PokemonAdapter;
import presentation.presenter.PokemonPresenter;
import presentation.presenter.PokemonPresenterImpl;

public class PokemonActivity extends AppCompatActivity implements PokemonDetailView {

    private PokemonPresenter presenter;
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

        presenter = new PokemonPresenterImpl(this);

        adapter.setOnItemClickListener(new PokemonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pokemon pokemon) {
                presenter.onPokemonClicked(pokemon);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Загружаем следующую порцию данных только при достижении конца списка
                if (
                        (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount - 1 <= (firstVisibleItemPosition + visibleItemCount)) {

                    presenter.fetchPokemonList(); // Вызываем загрузку следующей порции данных
                }
            }
        });
        presenter.fetchPokemonList();
    }

    @Override
    public void showPokemonList(List<Pokemon> pokemonList) {
        adapter.setData(pokemonList);
    }

    @Override
    public void showPokemonDetail(String pokemonId) {
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        intent.putExtra("pokemon_id", pokemonId);
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean isLoading)  {
        ProgressBar progressBar = findViewById(R.id.progressBar); // Пример: используйте свой элемент ProgressBar
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String errorMessage) {
        // Show error message
    }
}
