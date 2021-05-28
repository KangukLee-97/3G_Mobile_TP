package com.example.cocktail.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cocktail.OriginalRecipeActivity;
import com.example.cocktail.R;
import com.example.cocktail.View.IngerInfo;
import com.example.cocktail.View.OriginalInfo;

import java.util.ArrayList;

public class IngredientAdpater extends RecyclerView.Adapter<IngredientAdpater.RecipeViewHolder> {
    private ArrayList<IngerInfo> mDataset;
    private Context context;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public RecipeViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public IngredientAdpater(Context context, ArrayList<IngerInfo> customList) {
        mDataset = customList;
        this.context=context;
    }

      @Override
    public IngredientAdpater.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient,parent, false);
        final RecipeViewHolder vh=new RecipeViewHolder(cardView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        CardView cardView=holder.cardView;
        TextView textView1=cardView.findViewById(R.id.texts);
        textView1.setText(mDataset.get(position).getName());
        TextView textView2=cardView.findViewById(R.id.explain);
        textView2.setText(mDataset.get(position).getContent());
        ImageView imageView=cardView.findViewById(R.id.imageView);
        Glide.with(context).load(mDataset.get(position).getImage()).into(imageView);

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
