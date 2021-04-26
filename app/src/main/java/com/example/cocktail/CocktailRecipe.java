package com.example.cocktail;

public class CocktailRecipe {
    String name;
    String tag;
    int resId;

    public CocktailRecipe(String name, String tag, int resId) {
        this.name = name;
        this.tag = tag;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public String toString() {
        return "CocktailRecipe{" +
                "name='" + name + '\'' +
                ", mobile='" + tag + '\'' +
                '}' ;
    }
}
