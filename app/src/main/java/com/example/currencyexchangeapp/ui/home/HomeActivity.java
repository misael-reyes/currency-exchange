package com.example.currencyexchangeapp.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.currencyexchangeapp.R;
import com.example.currencyexchangeapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    // esto es para el viewBinding
    private ActivityHomeBinding binding;

    // atributos para el archivo sharedpreference
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // viewmodel
    HomeViewModel viewModel;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // conectamos el view binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // iniciamos las configuraciones

        initConfiguration();
        initObserver();
        initListeners();
    }

    private void initConfiguration() {
        // configuramos el toolbar como el actionbar
        Toolbar toolbar = binding.toolbarHome;
        toolbar.setTitle("App Converter");
        setSupportActionBar(toolbar);

        // iniciamos el preference
        sp = getSharedPreferences("Theme_configuration", Context.MODE_PRIVATE);
        editor = sp.edit();

        // inicializamos el viewmodel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // colocamos el tema
        setDayNight();
    }

    private void initListeners() {
        binding.switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.switchTheme.isChecked()) updateTheme(0);
                else updateTheme(1);
            }
        });

        // example
        binding.btnPais1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.agregar();
            }
        });
    }

    private void initObserver() {
        viewModel.getMausan().observe(this, contador -> {
            Toast.makeText(HomeActivity.this, "el contador esta en "+contador, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateTheme(int num_theme) {
        editor.putInt("Theme", num_theme);
        editor.apply();
        setDayNight();
    }

    private void setDayNight() {
        // necesitamos obtener la configuracion en un archivo clave-valor

        int theme = sp.getInt("Theme", 1);

        if(theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES); // dark
            binding.switchTheme.setChecked(true);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO); // light
            binding.switchTheme.setChecked(false);
        }
    }
}