package com.Gwyddyon.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class First_form extends AppCompatActivity{

    private String value_form,age ="",name="";
    private Animation an_pikachu, an_pie,an_pikachu_out;//, an_pie_out;
    View view;
    MediaPlayer mediaPlayer;

    @Override
    public void onBackPressed() {
        if(age !="" && name!=""){
            super.onBackPressed();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(), "¡Ingresa los datos antes de continuar!", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_form);


        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(First_form.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        //ediaPlayerTheme.release();

        //mediaPlayerTheme.seekTo(0);

        //();


        mediaPlayer = MediaPlayer.create(this,R.raw.pikachu);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();

        //getSupportActionBar().hide();
        //poniendo fuentes
        MaterialButton button = (MaterialButton)findViewById(R.id.button_set_name);
        TextView textViewPokedex = (TextView)findViewById(R.id.title_name);
        EditText et_nombre = (EditText)findViewById(R.id.nombre_edit);
        TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.til_nombre);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"Fonts/pokemon_pixel_font.ttf");
        textViewPokedex.setTypeface(typeface);
        et_nombre.setTypeface(typeface);
        textInputLayout.setTypeface(typeface);
        button.setTypeface(typeface);

        //animaciones
        AppCompatImageView imageViewPika = (AppCompatImageView)findViewById(R.id.pikachu);
        AppCompatImageView imageViewPie = (AppCompatImageView)findViewById(R.id.pie);
        AppCompatImageView imageViewLhand = (AppCompatImageView)findViewById(R.id.lhand);
        AppCompatImageView imageViewRhand = (AppCompatImageView)findViewById(R.id.rhand);
        LinearLayout linear_name = (LinearLayout)findViewById(R.id.linear_name);
        an_pikachu = AnimationUtils.loadAnimation(this,R.anim.anim_pikachu);
        an_pie = AnimationUtils.loadAnimation(this,R.anim.anim_pie);
        an_pikachu_out = AnimationUtils.loadAnimation(this,R.anim.anim_pikachu_out);
        //an_pie_out = AnimationUtils.loadAnimation(this,R.anim.anim_pie_out);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.LayoutFirstForm);

        new Thread(new Runnable() {
            public void run() {
                imageViewPika.setAnimation(an_pikachu);
                textViewPokedex.setAnimation(an_pikachu);
                imageViewPie.setAnimation(an_pie);
                imageViewLhand.setAnimation(an_pikachu);
                imageViewRhand.setAnimation(an_pikachu);
                linear_name.setAnimation(an_pie);
                button.setAnimation(an_pie);

            }
        }).start();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String text_option = textInputLayout.getEditText().getHint().toString();
                value_form = et_nombre.getText().toString();

                switch (text_option.toLowerCase().trim()){
                    case "nombre":

                        if(value_form.trim().length()<=1){
                            Snackbar.make(view, "¡Escribe el nombre del entrenador!", Snackbar.LENGTH_LONG)
                                    .setAction(null, null).show();
                        }else{
                            name = value_form;
                            myEditor.putString("NAME", name);
                            imageViewPika.startAnimation(an_pikachu_out);
                            //textViewPokedex.startAnimation(an_pikachu_out);
                            //imageViewPie.startAnimation(an_pie_out);
                            imageViewLhand.startAnimation(an_pikachu_out);
                            imageViewRhand.startAnimation(an_pikachu_out);
                            imageViewPika.postOnAnimationDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageViewLhand.setVisibility(View.INVISIBLE);
                                    imageViewRhand.setVisibility(View.INVISIBLE);
                                    imageViewPika.setVisibility(View.INVISIBLE);

                                    coordinatorLayout.setBackgroundColor(Color.parseColor("#FF8802"));
                                    //imageViewPika.setImageResource(R.mipmap.charmander_face);
                                    imageViewPika.setImageResource(R.mipmap.charmander_face);
                                    //android.view.ViewGroup.LayoutParams params = imageViewPika.getLayoutParams();
                                    //params.(left, top, right, bottom);
                                    CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)imageViewPika.getLayoutParams();
                                    lp.setMargins(0,30,0,0);
                                    imageViewPika.setLayoutParams(lp);
                                    //layout_marginTop="-65dp"
                                    imageViewPika.startAnimation(an_pikachu);
                                    imageViewPika.setVisibility(View.VISIBLE);
                                    //imageViewPie.startAnimation(an_pie);
                                    textInputLayout.getEditText().setText("");
                                    textInputLayout.getEditText().setHint("Edad");
                                    textInputLayout.setHint("Edad");
                                    textInputLayout.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

                                    //MediaPlayer mediaPlayer = new MediaPlayer();
                                    mediaPlayer.release();
                                    mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.charmander);
                                    mediaPlayer.seekTo(0);
                                    mediaPlayer.start();

                                }
                            },1000);
                            /*an_pikachu_out.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });*/
                            //linear_name.startAnimation(an_pie_out);
                            //button.startAnimation(an_pie_out);

                            //slide.addAnimationListener(new AnimationListener(){ public void onAnimationStart(Animation a){} public void onAnimationRepeat(Animation a){} public void onAnimationEnd(Animation a){ NameOfParentClass.this.removeChar(); } });

                            /*imageViewLhand.setVisibility(View.INVISIBLE);
                            imageViewRhand.setVisibility(View.INVISIBLE);
                            imageViewPika.setVisibility(View.INVISIBLE);

                            coordinatorLayout.setBackgroundColor(Color.parseColor("#FF8802"));
                            //imageViewPika.setImageResource(R.mipmap.charmander_face);
                            imageViewPika.setImageResource(R.mipmap.charmander_face);
                            imageViewPika.setAnimation(an_pikachu);
                            imageViewPika.setVisibility(View.VISIBLE);
                            //imageViewPie.startAnimation(an_pie);
                            textInputLayout.getEditText().setText("");
                            textInputLayout.getEditText().setHint("Edad");
                            textInputLayout.setHint("Edad");*/


                        }
                        break;
                    case "edad":
                        if(value_form.trim().length()<=1){
                            Snackbar.make(view, "¡Escribe tu edad!", Snackbar.LENGTH_LONG).show();
                        }else{
                            age = value_form;
                            myEditor.putString("AGE", age);
                            finish();
                            myEditor.commit();
                            /*imageViewPika.startAnimation(an_pikachu_out);
                            textViewPokedex.startAnimation(an_pikachu_out);
                            imageViewPie.startAnimation(an_pie_out);
                            imageViewLhand.startAnimation(an_pikachu_out);
                            imageViewRhand.startAnimation(an_pikachu_out);
                            linear_name.startAnimation(an_pie_out);
                            button.startAnimation(an_pie_out);*/

                        }
                        break;

                }




            }
        });

    }
    //myEditor.putString("NAME", "Alice");
}