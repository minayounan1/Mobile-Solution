package com.example.mobilesolutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesolutions.databinding.ItemLayoutBinding;
import com.example.mobilesolutions.db.CartItemList;


import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                parent, false);
        return new ViewHolder(v);
    }
    private List<CartItemList> data;
    public ViewAdapter(List<CartItemList> data) {
        this.data = data;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.itemTV.setText(data.get(position).getItemName());
        holder.binding.storeTV.setText(data.get(position).getStoreName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLayoutBinding.bind(itemView);
            binding.getRoot().setOnClickListener(view -> {
                int mPosition = getLayoutPosition();

                CartItemList element = ViewAdapter.this.data.get(mPosition);
                element.setItemName("done");
                data.set(mPosition, element);

                ViewAdapter.this.notifyDataSetChanged();
            });
        }
    }
}
