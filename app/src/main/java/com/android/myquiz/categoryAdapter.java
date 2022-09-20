package com.android.myquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewHolder> {

    Context context;
    ArrayList<categoryModel> categoryModels;

    public categoryAdapter(Context context, ArrayList<categoryModel> categoryModels){
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @NotNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,null);
        return new categoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull categoryAdapter.categoryViewHolder holder, int position) {
        categoryModel model = categoryModels.get(position);

        holder.textView.setText(model.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                        alertDialog.setTitle("Rules..");
                        alertDialog.setMessage("1. This is rules \n2. This is rules  \n3. This is rules \n4. This is rules \n\nAll the best! 😊");
                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context, QuizActivity.class);
                                intent.putExtra("categoryId",model.getCategoryId());
                                context.startActivity(intent);
                            }
                        }).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();

    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public categoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.category);

        }
    }
}
