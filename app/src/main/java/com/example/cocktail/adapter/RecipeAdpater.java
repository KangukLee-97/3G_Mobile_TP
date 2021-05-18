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

import com.example.cocktail.CustomRecipeActivity;
import com.example.cocktail.R;
import com.example.cocktail.View.AddInfo;

import java.util.ArrayList;

public class    RecipeAdpater extends RecyclerView.Adapter<RecipeAdpater.RecipeViewHolder> {
    private ArrayList<AddInfo> mDataset;
    private Fragment fragment;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public RecipeViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public RecipeAdpater(Fragment fragment, ArrayList<AddInfo> customList) {
        mDataset = customList;
        this.fragment=fragment;
    }

      @Override
    public RecipeAdpater.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent, false);
        final RecipeViewHolder vh=new RecipeViewHolder(cardView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        CardView cardView=holder.cardView;
        TextView textView1=cardView.findViewById(R.id.texts);
        textView1.setText(mDataset.get(position).getTitle());

        TextView textView2=cardView.findViewById(R.id.textTag);
        textView2.setText("taste : " + mDataset.get(position).getTaste() + "alcohol : " + mDataset.get(position).getAlcohol());
        ImageView imageView=cardView.findViewById(R.id.imageView);
        imageView.setImageBitmap(StringToBitmap(mDataset.get(position).getImage()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
                Intent intent=new Intent(v.getContext(), CustomRecipeActivity.class);
                intent.putExtra("Title", mDataset.get(position).getTitle());
                intent.putExtra("Image", mDataset.get(position).getImage());
                context.startActivity(intent);
            }
        });
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
