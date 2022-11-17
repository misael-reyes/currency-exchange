package com.example.currencyexchangeapp.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyexchangeapp.R;
import com.example.currencyexchangeapp.data.model.Rate;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private final List<Rate> rates;

    public CurrencyAdapter(List<Rate> rates) {
        this.rates = rates;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CurrencyViewHolder(layoutInflater.inflate(R.layout.item_currency, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Rate item = rates.get(position);
        holder.render(item);
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }
}
