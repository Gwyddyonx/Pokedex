package Entities;

import android.annotation.SuppressLint;

import static java.lang.String.format;

public class PokemonsVo {
    private String name;
    private int id;
    private String image_url;

    public PokemonsVo(String name, int id) {
        this.name = name;
        this.id = id;
        String format="%03d";
        @SuppressLint("DefaultLocale") String img= String.format(format, id);
        img += ".png";
        setImage_url(img);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
