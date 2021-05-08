package com.example.cocktail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail.R;
import com.example.cocktail.View.AddInfo;

import java.util.ArrayList;

public class RecipeAdpater extends RecyclerView.Adapter<RecipeAdpater.RecipeViewHolder> {
    private ArrayList<AddInfo> mDataset;
    private Activity activity;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public RecipeViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public RecipeAdpater(Activity activity, ArrayList<AddInfo> customList) {
        mDataset = customList;
        this.activity=activity;
    }

    @Override
    public RecipeAdpater.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent, false);
        final RecipeViewHolder vh=new RecipeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
    TextView textView=holder.textView.findViewById(R.id.texts);
    textView.setText(mDataset.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
