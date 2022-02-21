package Entities;

public class PokemonVo {

    private String name;
    private String description;
    private String type;
    private String type2;
    private int id;
    private String string_id;
    private String weight;


    private String height;
    private String speed;
    private String url_image;



    public PokemonVo(String name, String description, String type, String type2, int id, String string_id, String weight, String height, String speed, String url_image) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.type2 = type2;
        this.id = id;
        this.string_id = string_id;
        this.weight = weight;
        this.height = height;
        this.speed = speed;
        this.url_image = url_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString_id() {
        return string_id;
    }

    public void setString_id(String string_id) {
        this.string_id = string_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

}
