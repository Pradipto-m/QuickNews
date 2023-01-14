package com.pradipto.quicknews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryModal> categories;
    private Context context;
    private ClickInterface clickInterface;

    public CategoryAdapter(ArrayList<CategoryModal> categories, Context context, CategoryAdapter.ClickInterface clickInterface) {
        this.categories = categories;
        this.context = context;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModal categoryModal = categories.get(position);
        holder.categoryText.setText(categoryModal.getCategory());

        holder.itemView.setOnClickListener( view -> clickInterface.OnCategoryClick(position) );
    }

    @Override
    public int getItemCount() { return categories.size(); }

    public interface ClickInterface{
        void OnCategoryClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryText);
        }
    }
}
