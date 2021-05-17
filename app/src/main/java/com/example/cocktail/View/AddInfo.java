package com.example.cocktail.View;

public class AddInfo {
    private String title;
    private String contents;
    private String image;
    private String taste;
    private String alcohol;
    private String base;
    private String tech;
    private String glass;
    private String color;
    private String link;
    private String publisher;

    public AddInfo(String title, String contents, String image, String taste, String alcohol,
                   String base, String tech, String glass, String color, String link, String publisher){
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.taste = taste;
        this.alcohol = alcohol;
        this.base = base;
        this.tech = tech;
        this.glass = glass;
        this.color = color;
        this.link = link;
        this.publisher = publisher;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getContents(){
        return this.contents;
    }
    public void setContents(String contents){
        this.contents = contents;
    }

    public String getImage(){
        return this.image;
    }
    public void setImage(String image){ this.image = image; }

    public String getTaste(){
        return this.taste;
    }
    public void setTaste(String taste){
        this.taste = taste;
    }

    public String getAlcohol(){
        return this.alcohol;
    }
    public void setAlcohol(String alcohol){
        this.title = alcohol;
    }

    public String getBase(){
        return this.base;
    }
    public void setBase(String base){
        this.base = base;
    }

    public String getTech(){
        return this.tech;
    }
    public void setTech(String tech){
        this.tech = tech;
    }

    public String getGlass(){
        return this.glass;
    }
    public void setGlass(String glass){
        this.glass = glass;
    }

    public String getColor(){
        return this.color;
    }
    public void setColor(String color){
        this.color = color;
    }

    public String getLink(){
        return this.link;
    }
    public void setLink(String link){
        this.link = link;
    }

    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String contents){
        this.publisher = publisher;
    }

}
