package com.Gwyddyon.pokedex;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import com.bumptech.glide.Glide;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import Entities.PokemonVo;
import Entities.PokemonsVo;

public class DetailPokemonActivity extends AppCompatActivity implements ListPokemonsFragment.OnFragmentInteractionListener{

    boolean is_trap = false;
    String id_pokemon;
    PokemonVo pokemonVo;
    TextToSpeech speech;
    String text_for_speak="";
    String description2="";
    String archive_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speech = new TextToSpeech(this.getApplication().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = speech.setLanguage(new Locale("es","CO"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(),"Lenguaje no soportado", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!text_for_speak.equals("")){
                            speech.speak(text_for_speak,TextToSpeech.QUEUE_FLUSH,null);
                        }
                    }
                }
            }
        });

        id_pokemon = String.valueOf(getIntent().getExtras().getInt("id_pokemon"));
        is_trap = getIntent().getExtras().getBoolean("is_trap");
        archive_image = getIntent().getExtras().getString("archive_image");

        setContentView(R.layout.activity_detail_pokemon);
        getPokemonDetail();

        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setContext(getApplicationContext());
        aboutFragment.setPokemonVo(pokemonVo);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_about,aboutFragment).commit();

        FloatingActionButton floating_pokeball_detail = (FloatingActionButton) findViewById(R.id.floating_pokeball_detail);

        floating_pokeball_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS){
                            int result = speech.setLanguage(new Locale("es","CO"));
                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(),"Lenguaje no soportado", Toast.LENGTH_SHORT).show();
                            }else{
                                if(!text_for_speak.equals("")){
                                    speech.speak(description2,TextToSpeech.QUEUE_FLUSH,null);
                                }
                            }
                        }
                    }
                });
            }
        });


    }
    @Override
    public void onPause(){
        if(speech !=null){
            speech.stop();
            speech.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @SuppressLint("DefaultLocale")
    public void getPokemonDetail(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String real_ulr="https://vt5gcxl3tk.execute-api.us-east-2.amazonaws.com/default/getPokemon?id_pokemon="+id_pokemon;
        URL url;
        HttpURLConnection conn;
        String pokemon_name,weight, height, speed, string_id, type1, type2, description;

        try {
            url = new URL(real_ulr);
            String type = "application/json";
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", type);

            //DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            //wr.writeBytes(jsonObject.toString());
            //wr.flush();
            //wr.close();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStreamRespose = conn.getInputStream();

                String linea;
                //StringBuilder respuestaCadena = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamRespose, "UTF-8"));

                String resultJson = null;
                String json;

                while ((linea = bufferedReader.readLine()) != null) {
                    resultJson = linea;
                }

                json = resultJson;
                JSONObject jsonObject = new JSONObject(resultJson);
                //JSONArray jsonArr;
                JSONObject jsonArr;


                //jsonArr = new JSONArray(jsonObject.getJSONArray("Items"));
                jsonArr = jsonObject.getJSONObject("Item");

                //for (int i = 0; i < jsonArr.length(); i++) {
                    //JSONObject jsonObjects = jsonArr.getJSONObject(i);
                    //JSONObject jsonObjects = jsonArr.get(i);
                pokemon_name = jsonArr.optString("Name");
                weight = jsonArr.optString("Weight(kg)");
                height = jsonArr.optString("Height(m)");
                speed = jsonArr.optString("Speed");
                type1 = jsonArr.optString("Type1");
                type2 = jsonArr.optString("Type2");
                description = jsonArr.optString("description");
                description2 = jsonArr.optString("description2");

                String format = "%03d";
                string_id = "#"+String.format(format, Integer.parseInt(id_pokemon));

                text_for_speak = pokemon_name+", "+description;
                String url_photo="https://pokemons-images.s3.us-east-2.amazonaws.com/";
                url_photo += archive_image;

                pokemonVo = new PokemonVo(pokemon_name, description, type1, type2, Integer.parseInt(id_pokemon), string_id, weight, height, speed,url_photo);
                ImageView photo = (ImageView) findViewById(R.id.photoPokemon);
                Glide.with(getApplicationContext()).load(pokemonVo.getUrl_image()).into(photo);

                /*
                TextView name_pokemon_about = (TextView) findViewById(R.id.name_pokemon_about);
                TextView des_pokemon_about = (TextView) findViewById(R.id.des_pokemon_about);
                TextView weight_pokemon_about = (TextView) findViewById(R.id.weight_pokemon_about);
                TextView height_pokemon_about = (TextView) findViewById(R.id.height_pokemon_about);
                ImageView photo = (ImageView) findViewById(R.id.photoPokemon);
                name_pokemon_about.setText(pokemonVo.getName());
                des_pokemon_about.setText(pokemonVo.getDescription());
                weight_pokemon_about.setText(pokemonVo.getWeight());
                height_pokemon_about.setText(pokemonVo.getHeight());
                String url_photo="https://pokemons-images.s3.us-east-2.amazonaws.com/";
                url_photo += archive_image;
                Glide.with(getApplicationContext()).load(url_photo).into(photo);
                 */

                //String name, String type, double id, String string_id, String weight, String height, String speed
                //}
                //Toast.makeText(getApplicationContext(), pass, Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "La Aplicación no está disponible en este momento", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException | JSONException e) {
            Toast.makeText(getApplicationContext(),"Error consultado lista de pokemons:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void speak(String text){
        //speech = new TextToSpeech(getApplicationContext(), this);
        Locale spanish = new Locale("es");
        int result = speech.setLanguage(Locale.getDefault());

        speech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
}
