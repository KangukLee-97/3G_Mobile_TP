package com.example.cocktail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail.R;
import com.example.cocktail.View.AddInfo;

import java.util.ArrayList;

public class    RecipeAdpater extends RecyclerView.Adapter<RecipeAdpater.RecipeViewHolder> {
    private ArrayList<AddInfo> mDataset;
    private Fragment fragment;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textView;

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
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(v.getContext(), PostActivity.class);
//                intent.putExtra("addInfo", (Serializable) mDataset.get(vh.getAdapterPosition()));
//                v.getContext().startActivity(intent);
            }
        });
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
