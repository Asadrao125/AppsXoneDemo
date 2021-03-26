package com.gexton.appsxonedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gexton.appsxonedemo.ItemDetailActivity;
import com.gexton.appsxonedemo.R;
import com.gexton.appsxonedemo.models.ItemsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    Context context;
    ArrayList<ItemsModel> addChildModels;
    int qty = 1;

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
        holder.itemName.setText("Item # " + addChildModels.get(position).itemName);
        holder.tvSellPrice.setText("$" + addChildModels.get(position).sellPrice);
        holder.tvMcp.setText("MCP: " + addChildModels.get(position).mcp + "  |  ICP: " + addChildModels.get(position).icp);
        holder.tvQoh.setText("QOH: " + addChildModels.get(position).qoh);
        holder.tvQoo.setText(addChildModels.get(position).qoo);
        holder.tvSubCategory.setText(addChildModels.get(position).subCategory);
        Picasso.get().load(addChildModels.get(position).imagePath).placeholder(R.mipmap.ic_launcher).into(holder.img);
        holder.tvQuantity.setText("" + qty);

        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty > 1) {
                    holder.tvQuantity.setText("" + qty--);
                }
            }
        });

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvQuantity.setText("" + qty++);
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = 1;
                holder.tvQuantity.setText("" + qty);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("ItemId",addChildModels.get(position).itemId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addChildModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvDescription, tvSubCategory, itemName, tvMcp, tvQoh, tvQoo, tvPlus, tvMinus, tvQuantity, tvSellPrice;
        ImageView img;
        Button btnAddToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvSubCategory = itemView.findViewById(R.id.tvSubCategory);
            itemName = itemView.findViewById(R.id.tvItemName);
            tvMcp = itemView.findViewById(R.id.tvMcp);
            tvQoh = itemView.findViewById(R.id.tvQoh);
            tvQoo = itemView.findViewById(R.id.tvQoo);
            img = itemView.findViewById(R.id.img);
            tvPlus = itemView.findViewById(R.id.tvPlus);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvSellPrice = itemView.findViewById(R.id.tvSellPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
