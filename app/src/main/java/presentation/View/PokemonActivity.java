package presentation.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.pokemonreview.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import data.RetrofitClient;
import domain.model.Pokemon;
import domain.usecase.PokemonFetchCallback;
import domain.usecase.PokemonInteractor;
import domain.usecase.PokemonInteractorImpl;
import presentation.PokemonAdapter;


public class PokemonActivity extends AppCompatActivity {
    private int offset = 0;
    private final int limit = 20;
    private boolean isLoading = false;

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private PokemonInteractor pokemonInteractor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PokemonAdapter();
        recyclerView.setAdapter(adapter);
        pokemonInteractor = new PokemonInteractorImpl(RetrofitClient.create());



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Загружаем следующую порцию данных только при достижении конца списка
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount - 1 <= (firstVisibleItemPosition + visibleItemCount)) {
                    fetchPokemonList();
                }
            }
        });
        fetchPokemonList();
    }

    private void fetchPokemonList() {
        if (isLoading) {
            return; // Already loading, skip
        }

        isLoading = true;

        pokemonInteractor.fetchPokemonList(offset, limit, new PokemonFetchCallback() {
            @Override
            public void onSuccess(List<Pokemon> newPokemonList) {
                isLoading = false;
                if (!newPokemonList.isEmpty()) {
                    adapter.addData(newPokemonList); // Добавляем новые данные в адаптер
                    offset += newPokemonList.size(); // Обновляем offset
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading = false;
                // Handle failure
            }
        });
    }


}