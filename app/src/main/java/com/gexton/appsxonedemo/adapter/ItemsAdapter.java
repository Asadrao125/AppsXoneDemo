package com.gexton.appsxonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gexton.appsxonedemo.R;
import com.gexton.appsxonedemo.models.ItemsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    Context context;
    ArrayList<ItemsModel> addChildModels;

    public ItemsAdapter(Context c, ArrayList<ItemsModel> message) {
        context = c;
        addChildModels = message;
    }

    @NonNull
    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.MyViewHolder holder, int position) {
        holder.tvCategory.setText(addChildModels.get(position).category);
        holder.tvDescription.setText(addChildModels.get(position).description);
        holder.tvIcp.setText(addChildModels.get(position).icp);
        holder.tvItemId.setText(addChildModels.get(position).itemId);
        holder.itemName.setText(addChildModels.get(position).itemName);
        holder.tvMcp.setText(addChildModels.get(position).mcp);
        holder.tvQoh.setText(addChildModels.get(position).qoh);
        holder.tvQoo.setText(addChildModels.get(position).qoo);

        Picasso.get().load(addChildModels.get(position).imagePath).placeholder(R.mipmap.ic_launcher).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return addChildModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory, tvDescription, tvIcp, tvItemId, itemName, tvMcp, tvQoh, tvQoo;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvIcp = itemView.findViewById(R.id.tvICP);
            tvItemId = itemView.findViewById(R.id.tvItemId);
            itemName = itemView.findViewById(R.id.tvItemName);
            tvMcp = itemView.findViewById(R.id.tvMcp);
            tvQoh = itemView.findViewById(R.id.tvQoh);
            tvQoo = itemView.findViewById(R.id.tvQoo);
            img = itemView.findViewById(R.id.img);
        }
    }
}
