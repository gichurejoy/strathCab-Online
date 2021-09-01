package com.example.strathcab.adapter.RecyclerViewPlaces;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.strathcab.R;
import com.example.strathcab.adapter.ClickListener;

public class PlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvPlaceName;
    ClickListener listener;

    public PlacesViewHolder(View itemView, ClickListener listener) {
        super(itemView);
        tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.listener.onClick(view, getAdapterPosition());
    }

}
