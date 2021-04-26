package com.example.cocktail;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class CocktailRecipeView extends LinearLayout {

    TextView textView, textView2;
    ImageView imageView;

    public CocktailRecipeView(Context context) {
        super(context);
        init(context);
    }

    public CocktailRecipeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cocktail_recipe_list, this, true);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
    }

    public void setName(String name) {
        textView.setText(name);
    }

    public void setTag(String tag) {
        textView2.setText(tag);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}
