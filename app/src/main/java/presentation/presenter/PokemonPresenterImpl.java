package presentation.presenter;


import android.content.Context;

import data.RetrofitClient;
import data.database.AppDatabase;
import domain.model.Pokemon;
import domain.usecase.PokemonFetchCallback;
import domain.usecase.PokemonInteractor;
import domain.usecase.PokemonInteractorImpl;
import presentation.view.PokemonDetailView;
import kotlinx.coroutines.GlobalScope;


import java.util.List;

public class PokemonPresenterImpl implements PokemonPresenter {

    private int offset = 0;

    private int limit=20;
    private PokemonInteractor interactor;
    private PokemonDetailView view;
    private boolean isFetching = false;


    public PokemonPresenterImpl(PokemonDetailView view, Context context) {
        this.view = view;
        this.interactor = new PokemonInteractorImpl(RetrofitClient.create(), context);
    }


    @Override
    public void fetchPokemonList() {
        if (isFetching) {
            return;
        }

        isFetching = true;
        view.showLoading(true);

        interactor.fetchPokemonList(offset, limit, new PokemonFetchCallback() {
            @Override
            public void onSuccess(List<Pokemon> newPokemonList) {
                isFetching = false;
                view.showLoading(false);
                view.showPokemonList(newPokemonList);
                offset += newPokemonList.size();

            }

            @Override
            public void onFailure(Throwable throwable) {
                isFetching = false;
                view.showLoading(false);
                view.showError("Failed to fetch data");
            }
        });
    }

    @Override
    public void onPokemonClicked(Pokemon pokemon) {
        view.showPokemonDetail(pokemon.getUrl());
    }
}
