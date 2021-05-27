package com.example.cocktail.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class    RecipeAdpater extends RecyclerView.Adapter<RecipeAdpater.RecipeViewHolder> {
    private ArrayList<AddInfo> mDataset;
    private Fragment fragment;
    private ArrayList id;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public RecipeViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public RecipeAdpater(Fragment fragment, ArrayList<AddInfo> customList, ArrayList uid) {
        mDataset = customList;
        this.fragment=fragment;
        id = uid;
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
        textView1.setText(mDataset.get(position).getName());
        ImageView imageView=cardView.findViewById(R.id.imageView);
        imageView.setImageBitmap(StringToBitmap(mDataset.get(position).getImage()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                int click = mDataset.get(position).getClick() + 1;
                db.collection("customs").document(String.valueOf(id.get(position))).update("click", click);
                Context context=v.getContext();
                Intent intent=new Intent(v.getContext(), CustomRecipeActivity.class);
                intent.putExtra("title", mDataset.get(position).getName());
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
