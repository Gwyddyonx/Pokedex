package com.Gwyddyon.pokedex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.Gwyddyon.pokedex.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Entities.PokemonsVo;

public class MainActivity extends AppCompatActivity implements
        ListPokemonsFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration appBarConfiguration;
    /*private ActivityMainBinding binding;*/
    private PokemonsVo pokemonsVo;
    private ArrayList<PokemonsVo> pokemonsArrayList = new ArrayList<>();
    private ListPokemonsFragment listPokemonsFragment;
    MediaPlayer mediaPlayerTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if(mediaPlayerTheme == null){
        mediaPlayerTheme = MediaPlayer.create(this,R.raw.atrapalos);
        mediaPlayerTheme.setVolume(50,50);
        mediaPlayerTheme.seekTo(0);
        mediaPlayerTheme.start();
        //}


        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        getAllPokemons();
        setContentView(R.layout.activity_main);
        listPokemonsFragment = new ListPokemonsFragment(pokemonsArrayList);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,listPokemonsFragment).commit();

        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_pokedex);



        TextView editToolbar = (TextView)findViewById(R.id.toobartitle);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),"Fonts/pokemon_pixel_font.ttf");
        editToolbar.setTypeface(typeface);
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_capturar);
        floatingActionButton.setTypeface(typeface);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity_next;
                activity_next = new Intent(MainActivity.this, ClassifierActivity.class);
                startActivity(activity_next);
            }
        });

        //setSupportActionBar(binding.toolbar);

        //setSupportActionBar(binding.toolbar);

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/



        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        String name = myPreferences.getString("NAME", "unknown");
        int age = Integer.parseInt(myPreferences.getString("AGE", "0"));

        if(name == "unknown" || age == 0){
            Intent activity_next;
            activity_next = new Intent(MainActivity.this, First_form.class);
            startActivity(activity_next);
            //finish();
        }else{
            Toast.makeText(getApplicationContext(),"Hola de nuevo "+name, Toast.LENGTH_SHORT).show();
        }

        myEditor.apply();


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    public void getAllPokemons(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String real_ulr="https://t822cwx7n9.execute-api.us-east-2.amazonaws.com/default/getPokemons";
        URL url;
        HttpURLConnection conn;
        String pokemon_name;
        int pokemon_id;

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
                JSONArray jsonArr;

                //jsonArr = new JSONArray(jsonObject.getJSONArray("Items"));
                jsonArr = jsonObject.getJSONArray("Items");

                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObjects = jsonArr.getJSONObject(i);
                    pokemon_name = jsonObjects.optString("Name");
                    pokemon_id = Integer.parseInt(jsonObjects.optString("id"));

                    pokemonsArrayList.add(new PokemonsVo(pokemon_name,pokemon_id));
                }

                Collections.sort(pokemonsArrayList, new Comparator<PokemonsVo>() {
                    @Override
                    public int compare(PokemonsVo p1, PokemonsVo p2) {
                        return new Integer(p1.getId()).compareTo(new Integer(p2.getId()));
                    }
                });
                //Toast.makeText(getApplicationContext(), pass, Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "La Aplicación no está disponible en este momento", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException | JSONException e) {
            Toast.makeText(getApplicationContext(),"Error consultado lista de pokemons:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}