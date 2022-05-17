package com.ngochien.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.databinding.StoreItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminStoreAdapter extends RecyclerView.Adapter<AdminStoreAdapter.AdminStoreVH> {
    Context context;
    ArrayList<StoreData> list;
    public AdminStoreListener listener;

    public AdminStoreAdapter(Context context, ArrayList<StoreData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdminStoreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoreItemBinding binding = StoreItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminStoreVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminStoreVH holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminStoreVH extends RecyclerView.ViewHolder {

        StoreItemBinding binding;

        public AdminStoreVH(StoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void onBind(StoreData item) {

            Picasso.with(context).load(item.image).into(binding.ivImage);
            binding.tvAdd.setText(item.address);
            binding.tvDetailAdd.setText(item.detailAddress);
            binding.tvBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickBook(item);
                }
            });
        }
    }

    public interface AdminStoreListener{
        void onClickBook(StoreData item);
    }
}
