package com.example.cocktail.View;

public class AddInfo {
    private String Name;
    private String 설명;
    private String ImageURL;
    private String Taste;
    private String Alcoholicity;
    private String Technique;
    private String Glass;
    private String Color;
    private String VideoLink;
    private String Garnish;
    private String Ingredients;
    private String Ingredients2;
    private String Ingredients3;
    private String Ingredients4;
    private String Ingredients5;
    private String Ingredients6;
    private String Ingredients7;
    private String Main_Alcohol;
    private String TPO;
    private String Tag;
    private int click;
    private String publisher;

    public AddInfo(String Name, String 설명, String ImageURL, String Taste, String Alcoholicity,
                   String Technique, String Glass, String Color, String VideoLink, String Garnish, String Ingredients,
                   String Ingredients2, String Ingredients3, String Ingredients4, String Ingredients5, String Ingredients6,
                   String Ingredients7, String Main_Alcohol, String TPO, String Tag, int click, String publisher){
        this.Name = Name;
        this.설명 = 설명;
        this.ImageURL = ImageURL;
        this.Taste = Taste;
        this.Alcoholicity = Alcoholicity;
        this.Technique = Technique;
        this.Glass = Glass;
        this.Color = Color;
        this.VideoLink = VideoLink;
        this.Garnish = Garnish;
        this.Ingredients = Ingredients;
        this.Ingredients2 = Ingredients2;
        this.Ingredients3 = Ingredients3;
        this.Ingredients4 = Ingredients4;
        this.Ingredients5 = Ingredients5;
        this.Ingredients6 = Ingredients6;
        this.Ingredients7 = Ingredients7;
        this.Main_Alcohol = Main_Alcohol;
        this.TPO = TPO;
        this.Tag = Tag;
        this.click = click;
        this.publisher = publisher;
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
        return this.ImageURL;
    }
    public void setImage(String ImageURL){ this.ImageURL = ImageURL; }

    public String getTaste(){
        return this.Taste;
    }
    public void setTaste(String Taste){
        this.Taste = Taste;
    }

    public String getAlcoholicity(){
        return this.Alcoholicity;
    }
    public void setAlcoholicity(String Alcoholicity){
        this.Alcoholicity = Alcoholicity;
    }

    public String getTechnique(){
        return this.Technique;
    }
    public void setTechnique(String Technique){
        this.Technique = Technique;
    }

    public String getGlass(){
        return this.Glass;
    }
    public void setGlass(String Glass){
        this.Glass = Glass;
    }

    public String getColor(){
        return this.Color;
    }
    public void setColor(String Color){
        this.Color = Color;
    }

    public String getLink(){
        return this.VideoLink;
    }
    public void setLink(String VideoLink){
        this.VideoLink = VideoLink;
    }

    public String getGarnish(){
        return this.Garnish;
    }
    public void setGarnish(String Garnish){ this.Garnish = Garnish; }

    public String getIngredients(){
        return this.Ingredients;
    }
    public void setIngredients(String Ingredients){
        this.Ingredients = Ingredients;
    }

    public String getIngredients2(){
        return this.Ingredients2;
    }
    public void setIngredients2(String Ingredients2){
        this.Ingredients2 = Ingredients2;
    }

    public String getIngredients3(){
        return this.Ingredients3;
    }
    public void setIngredients3(String Ingredients3){
        this.Ingredients3 = Ingredients3;
    }

    public String getIngredients4(){
        return this.Ingredients4;
    }
    public void setIngredients4(String Ingredients4){
        this.Ingredients4 = Ingredients4;
    }

    public String getIngredients5(){
        return this.Ingredients5;
    }
    public void setIngredients5(String Ingredients5){
        this.Ingredients5 = Ingredients5;
    }

    public String getIngredients6(){
        return this.Ingredients6;
    }
    public void setIngredients6(String Ingredients6){
        this.Ingredients5 = Ingredients6;
    }

    public String getIngredients7(){
        return this.Ingredients7;
    }
    public void setIngredients7(String Ingredients7){
        this.Ingredients7 = Ingredients7;
    }

    public String getMain_Alcohol(){
        return this.Main_Alcohol;
    }
    public void setMain_Alcohol(String Main_Alcohol){
        this.Main_Alcohol = Main_Alcohol;
    }

    public String getTPO(){
        return this.TPO;
    }
    public void setTPO(String TPO){
        this.TPO = TPO;
    }

    public String getTag(){
        return this.Tag;
    }
    public void setTag(String Tag){
        this.Tag = Tag;
    }

    public int getClick(){
        return this.click;
    }
    public void setClick(int link){
        this.click = click;
    }

    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String contents){
        this.publisher = publisher;
    }

}
