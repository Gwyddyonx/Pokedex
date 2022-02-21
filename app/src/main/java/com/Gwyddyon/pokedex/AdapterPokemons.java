package com.Gwyddyon.pokedex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import Entities.PokemonsVo;

import static android.os.FileUtils.copy;

public class AdapterPokemons extends RecyclerView.Adapter<AdapterPokemons.PokemonsViewHolder> implements View.OnClickListener {


    private ArrayList<PokemonsVo> pokemonsArrayList;
    private Context context;
    private View.OnClickListener listener;

    public AdapterPokemons(ArrayList<PokemonsVo> pokemonsArrayList, Context context) {
        this.pokemonsArrayList = pokemonsArrayList;
        this.context=context;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    //@org.jetbrains.annotations.NotNull
    @Override
    public AdapterPokemons.PokemonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item_list,null,false);
        view.setOnClickListener(this);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PokemonsViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull AdapterPokemons.PokemonsViewHolder holder, int position) {
        holder.txt_pokemon.setText(pokemonsArrayList.get(position).getName());

        holder.txt_id.setText("#"+pokemonsArrayList.get(position).getId());

        String url="https://pokemons-images.s3.us-east-2.amazonaws.com/";
        String archive= pokemonsArrayList.get(position).getImage_url();
        url += archive;

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Fonts/pokemon_pixel_font.ttf");

        holder.txt_id.setTypeface(typeface);
        holder.txt_pokemon.setTypeface(typeface);
        ColorMatrix colorMatrix = new ColorMatrix();


        if((pokemonsArrayList.get(position).getId() % 2) == 0){
            holder.img_photo.setScaleType(ImageView.ScaleType.FIT_END);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.LEFT;

            holder.txt_pokemon.setLayoutParams(params);
            holder.txt_id.setLayoutParams(params);
        }



        //Bitmap bitmap = loadBitmap(url);
        //holder.img_photo.setImageBitmap(bitmap);
        if(pokemonsArrayList.get(position).getId() == 1 || pokemonsArrayList.get(position).getId() == 2|| pokemonsArrayList.get(position).getId() == 5|| pokemonsArrayList.get(position).getId() == 4){
        }else{
            colorMatrix.setSaturation(0);
        }

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        holder.img_photo.setColorFilter(colorFilter);

        Glide.with(context).load(url).into(holder.img_photo);
    }

    @Override
    public int getItemCount() {
        return pokemonsArrayList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), 4 * 1024);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 4 * 1024);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        } catch (IOException e) {
            //Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            //closeStream(in);
            //closeStream(out);
        }

        return bitmap;
    }

    public void SetOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



    public class PokemonsViewHolder extends RecyclerView.ViewHolder{
        TextView txt_pokemon, txt_id;
        ImageView img_photo;

        public PokemonsViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.photoPokemon);
            txt_pokemon = itemView.findViewById(R.id.name_pokemon);
            txt_id = itemView.findViewById(R.id.id_pokemon);
        }
    }
}
