package com.example.cocktail.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cocktail.CustomRecipeActivity;
import com.example.cocktail.OriginalRecipeActivity;
import com.example.cocktail.R;
import com.example.cocktail.View.AddInfo;
import com.example.cocktail.View.OriginalInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OriginalRecipeAdpater extends RecyclerView.Adapter<OriginalRecipeAdpater.RecipeViewHolder> {
    private ArrayList<OriginalInfo> mDataset;
    private Fragment fragment;
    private ArrayList id;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public RecipeViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public OriginalRecipeAdpater(Fragment fragment, ArrayList<OriginalInfo> customList, ArrayList uid) {
        mDataset = customList;
        this.fragment=fragment;
        id = uid;
    }

      @Override
    public OriginalRecipeAdpater.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent, false);
        final RecipeViewHolder vh=new RecipeViewHolder(cardView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        CardView cardView=holder.cardView;
        TextView textView1=cardView.findViewById(R.id.texts);
        textView1.setText(mDataset.get(position).getName());
        ImageView imageView=cardView.findViewById(R.id.imageView);
        Glide.with(fragment).load(mDataset.get(position).getImage()).into(imageView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
                Intent intent=new Intent(v.getContext(), OriginalRecipeActivity.class);
                intent.putExtra("title", mDataset.get(position).getName());
                intent.putExtra("image", mDataset.get(position).getImage());
                intent.putExtra("content", mDataset.get(position).getContent());
                intent.putExtra("taste", mDataset.get(position).getTaste());
                intent.putExtra("alcoholicity", mDataset.get(position).getAlcoholicity());
                intent.putExtra("technique", mDataset.get(position).getTechnique());
                intent.putExtra("glass", mDataset.get(position).getGlass());
                intent.putExtra("color", mDataset.get(position).getColor());
                intent.putExtra("link", mDataset.get(position).getLink());
                intent.putExtra("garnish", mDataset.get(position).getGarnish());
                intent.putExtra("main_Alcohol", mDataset.get(position).getMain_Alcohol());
                intent.putExtra("ingredients", mDataset.get(position).getIngredients());
                intent.putExtra("ingredients2", mDataset.get(position).getIngredients2());
                intent.putExtra("ingredients3", mDataset.get(position).getIngredients3());
                intent.putExtra("ingredients4", mDataset.get(position).getIngredients4());
                intent.putExtra("ingredients5", mDataset.get(position).getIngredients5());
                intent.putExtra("ingredients6", mDataset.get(position).getIngredients6());
                intent.putExtra("ingredients7", mDataset.get(position).getIngredients7());

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
