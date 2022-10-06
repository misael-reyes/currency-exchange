package com.example.currencyexchangeapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.currencyexchangeapp.R;
import com.example.currencyexchangeapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    // esto es para el viewBinding
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // conectamos el view binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}