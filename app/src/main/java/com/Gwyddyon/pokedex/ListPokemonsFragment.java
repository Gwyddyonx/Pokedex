package com.Gwyddyon.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Entities.PokemonsVo;

public class ListPokemonsFragment extends Fragment {

    private ArrayList<PokemonsVo> pokemonsArrayList = new ArrayList<>();
    RecyclerView recyclerView;

    OnFragmentInteractionListener mlistener;

    public ListPokemonsFragment(ArrayList<PokemonsVo> pokemonsArrayList) {
        this.pokemonsArrayList = pokemonsArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.list_pokemons_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterPokemons adapterPokemons = new AdapterPokemons(pokemonsArrayList,getContext());

        adapterPokemons.notifyDataSetChanged();

        recyclerView.setAdapter(adapterPokemons);

        adapterPokemons.SetOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DetailPokemonActivity.class);
                Integer id_pokemon = pokemonsArrayList.get(recyclerView.getChildAdapterPosition(v)).getId();
                intent.putExtra("id_pokemon",id_pokemon);
                intent.putExtra("is_atrap",true);
                intent.putExtra("archive_image",pokemonsArrayList.get(recyclerView.getChildAdapterPosition(v)).getImage_url());
                getActivity().startActivity(intent);
            }
        });

        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
