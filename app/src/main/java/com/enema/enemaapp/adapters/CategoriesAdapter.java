package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.CategoriesData;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<CategoriesData> categoriesDataList;
    private Context context;

    public CategoriesAdapter(List<CategoriesData> categoriesDataList, Context context) {
        this.categoriesDataList = categoriesDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesAdapter.CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_list_item, viewGroup, false);

        return new CategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoriesViewHolder categoriesViewHolder, int i) {

        CategoriesData cd = categoriesDataList.get(i);
        categoriesViewHolder.txtCategoryName.setText(cd.getCategories());

    }

    @Override
    public int getItemCount() {
        return categoriesDataList.size();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCategoryName;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);

        }
    }
}
