package com.example.cocktail.View;

public class IngerInfo {
    private String Name;
    private String 설명;
    private String Image;

    public IngerInfo(String Name, String 설명, String Image){
        this.Name = Name;
        this.설명 = 설명;
        this.Image = Image;
    }

    public String getName(){
        return this.Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }

    public String getContent(){
        return this.설명;
    }
    public void setContent(String 설명){
        this.설명 = 설명;
    }

    public String getImage(){
        return this.Image;
    }
    public void setImage(String Image){ this.Image = Image; }

}
