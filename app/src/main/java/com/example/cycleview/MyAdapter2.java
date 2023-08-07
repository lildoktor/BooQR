package com.example.cycleview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {
    private Context context;
    private List<DataClass2> dataList;
    boolean switcher = false;

    public MyAdapter2(Context context, List<DataClass2> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2, parent, false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        switch (dataList.get(position).getType()) {
            case 1:
                holder.recImage.setImageResource(R.drawable.baseline_smart_display_60);
                break;
            case 2:
                holder.recImage.setImageResource(R.drawable.baseline_image_60);
                break;
            case 3:
                holder.recImage.setImageResource(R.drawable.baseline_audiotrack_60);
                break;
            case 4:
                holder.recImage.setImageResource(R.drawable.baseline_question_mark_60);
                break;
            case 5:
                holder.recImage.setImageResource(R.drawable.baseline_draw_60);
                break;
        }
        holder.recTitle.setText(dataList.get(position).getTitle());
        if(dataList.get(position).getPageNum() < 0){
            holder.recDesc.setVisibility(View.GONE);
            holder.recDescPage.setVisibility(View.GONE);
        }else {
            holder.recDesc.setText(String.valueOf(dataList.get(position).getPageNum()));
        }
//        holder.recCard.setOnClickListener(view -> {
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
//            intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
//            intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
//            intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
//            context.startActivity(intent);
//        });
//
//        holder.option.setOnClickListener(view -> {
//            if(!switcher) {
//                holder.delete.setVisibility(View.VISIBLE);
//                holder.edit.setVisibility(View.VISIBLE);
//                switcher = true;
//            }else{
//                holder.delete.setVisibility(View.GONE);
//                holder.edit.setVisibility(View.GONE);
//                switcher = false;
//            }
//        });
//
//        holder.edit.setOnClickListener(view -> {
//            Intent intent = new Intent(context, UpdateActivity.class);
//            intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
//            intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
//            intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
//            intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
//            context.startActivity(intent);
//        });
//
//
//        holder.delete.setOnClickListener(view -> {
//
//            //TODO: delete subdirectory
//
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UID");
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//
//            StorageReference storageReference = storage.getReferenceFromUrl(dataList.get(holder.getAdapterPosition()).getDataImage());
//            storageReference.delete().addOnSuccessListener(unused -> {
//                reference.child(dataList.get(holder.getAdapterPosition()).getKey()).removeValue().addOnSuccessListener(unused1 -> {
//                    holder.delete.setVisibility(View.GONE);
//                    holder.edit.setVisibility(View.GONE);
//                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
//                });
//            });
//        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass2> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder2 extends RecyclerView.ViewHolder {
    ImageView recImage, option, delete, edit;
    TextView recTitle, recDesc, recDescPage;
    CardView recCard;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDescPage = itemView.findViewById(R.id.Page);
        recDesc = itemView.findViewById(R.id.PageNum);
        recTitle = itemView.findViewById(R.id.recTitle);

//        option = itemView.findViewById(R.id.dot);
//        delete = itemView.findViewById(R.id.delete);
//        edit = itemView.findViewById(R.id.edit);
    }
}
