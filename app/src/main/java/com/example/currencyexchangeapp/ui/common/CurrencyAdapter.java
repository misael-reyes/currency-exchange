package com.example.currencyexchangeapp.ui.common;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyexchangeapp.R;
import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.databinding.ItemCurrencyBinding;

import java.util.List;
import java.util.function.Function;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private final List<Rate> rates;
    private final Function<Rate, Void> onClickListener;

    public CurrencyAdapter(List<Rate> rates, Function<Rate, Void> onClickListener) {
        this.rates = rates;
        this.onClickListener = onClickListener;
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
        holder.render(item, onClickListener);
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }
}
