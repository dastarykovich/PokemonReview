package presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonreview.R;

import java.util.ArrayList;
import java.util.List;

import domain.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<Pokemon> data = new ArrayList<>();

    public void setData(List<Pokemon> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = data.get(position);
        holder.nameTextView.setText(pokemon.getName());
        holder.urlTextView.setText(pokemon.getUrl());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView urlTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            urlTextView=itemView.findViewById(R.id.url);
        }
    }
}
