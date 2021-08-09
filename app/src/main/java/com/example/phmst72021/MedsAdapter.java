package com.example.phmst72021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.MyViewHolder> {

    Context context;

    ArrayList<MedicationInfo> list;
    private OnMedListener mOnMedListener;

    public MedsAdapter(Context context, ArrayList<MedicationInfo> list,OnMedListener onMedListener ) {
        this.context = context;
        this.list = list;
        this.mOnMedListener = onMedListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.med_items,parent,false);
        return new MyViewHolder(v,mOnMedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MedsAdapter.MyViewHolder holder, int position) {

        MedicationInfo MedItem = list.get(position);
        holder.medNameItem.setText(MedItem.getMedName());
        holder.expDateItem.setText(MedItem.getExpDate());
        holder.amountItem.setText(MedItem.getAmountGiven());
        holder.dosageItem.setText(MedItem.getDosageAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medNameItem, dosageItem, amountItem, expTitleItem, expDateItem;
        OnMedListener onMedListener;

        public MyViewHolder(@NonNull View itemView, OnMedListener onMedListener)
        {
            super(itemView);

            medNameItem = itemView.findViewById(R.id.MedNameItem);
            dosageItem = itemView.findViewById(R.id.DosageMedItem);
            amountItem = itemView.findViewById(R.id.AmountMedItem);
            expTitleItem = itemView.findViewById(R.id.ExpDateTitleItem);
            expDateItem = itemView.findViewById(R.id.ExpDateActItem);

            this.onMedListener = onMedListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMedListener.onMedClick(getAdapterPosition());
        }
    }
    public interface OnMedListener{
        void onMedClick(int position);
    }
}
