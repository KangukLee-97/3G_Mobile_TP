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

public class RankAdpater extends RecyclerView.Adapter<RankAdpater.RecipeViewHolder> {
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

    public RankAdpater(Fragment fragment, ArrayList<AddInfo> customList, ArrayList uid) {
        mDataset = customList;
        this.fragment=fragment;
        id = uid;
    }

    @Override
    public RankAdpater.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_recipe,parent, false);
        final RecipeViewHolder vh=new RecipeViewHolder(cardView);
        return vh;
    }

   @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        CardView cardView=holder.cardView;
        TextView rank = cardView.findViewById(R.id.rank);
        rank.setText(String.valueOf(position + 1));

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
                intent.putExtra("name", mDataset.get(position).getName());
//                intent.putExtra("Image", mDataset.get(position).getImage());
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
