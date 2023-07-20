package com.example.dailyhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainDrinkAdapter extends RecyclerView.Adapter<MainDrinkAdapter.ViewHolder>{
    Activity activity;
    ArrayList<BottleInfo> bottleInfos;
    private MainDrinkAdapter.OnItemClick onItemClick;


    public MainDrinkAdapter(Activity activity, ArrayList<BottleInfo> bottleInfos, MainDrinkAdapter.OnItemClick onItemClick) {
        this.activity = activity;
        this.bottleInfos = bottleInfos;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainDrinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_drink_item, parent, false);
        return new MainDrinkAdapter.ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MainDrinkAdapter.ViewHolder holder, int position) {
        BottleInfo bottleInfo = bottleInfos.get(position);

        holder.amountTV.setText(bottleInfo.getAmount() + " ml");
        if (bottleInfo.getStatus())
            holder.bottleImgView.setImageResource(R.drawable.ic_bottle_full_foreground);
        else
            holder.bottleImgView.setImageResource(R.drawable.ic_bottle_empty_foreground);
    }

    @Override
    public int getItemCount() {
        return bottleInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView amountTV;
        ImageView bottleImgView;
        private ItemClickListener itemClickListener;
        private MainDrinkAdapter.OnItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, MainDrinkAdapter.OnItemClick onItemClick) {
            super(itemView);
            amountTV = itemView.findViewById(R.id.amountWaterTV);
            bottleImgView = itemView.findViewById(R.id.bottleImageView);
            this.onItemClick = onItemClick;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onBottleClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public interface OnItemClick {
        void onBottleClick(int position);
    }
}
