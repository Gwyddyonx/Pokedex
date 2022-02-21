package com.Gwyddyon.pokedex;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import Entities.PokemonVo;

public class AboutFragment extends Fragment {
    PokemonVo pokemonVo;

    Context context;

    public void setPokemonVo(PokemonVo pokemonVo) {
        this.pokemonVo = pokemonVo;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout ll = (LinearLayout )inflater.inflate(R.layout.about, container, false);
        View view = inflater.inflate(R.layout.about,container,false);

        TextView id_pokemon_about = (TextView) ll.findViewById(R.id.id_pokemon_about);
        TextView name_pokemon_about = (TextView) ll.findViewById(R.id.name_pokemon_about);
        TextView des_pokemon_about = (TextView) ll.findViewById(R.id.des_pokemon_about);
        TextView weight_pokemon_about = (TextView) ll.findViewById(R.id.weight_pokemon_about);
        TextView height_pokemon_about = (TextView) ll.findViewById(R.id.height_pokemon_about);
        ImageView photo = (ImageView) ll.findViewById(R.id.photoPokemon);
        id_pokemon_about.setText(pokemonVo.getString_id());
        name_pokemon_about.setText(pokemonVo.getName());
        des_pokemon_about.setText(pokemonVo.getDescription());
        weight_pokemon_about.setText(pokemonVo.getWeight()+" kg.");
        height_pokemon_about.setText(pokemonVo.getHeight()+" m.");
        //Glide.with(context).load(pokemonVo.getUrl_image()).into(photo);

        return ll;
    }
}
