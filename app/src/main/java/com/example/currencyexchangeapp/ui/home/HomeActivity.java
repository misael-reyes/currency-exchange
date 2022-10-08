package com.example.currencyexchangeapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

//        binding.sw1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (binding.sw1.isChecked())
//                    setDayNight(0);
//                else
//                    setDayNight(1);
//            }
//        });
    }

    public void setDayNight(int theme){
        //SharedPreferences sp = getSharedPreferences("SP", this.MODE_PRIVATE);
        //int theme = sp.getInt("Theme", 1);
        if(theme==0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    // metodo para mostrar el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_theme, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_theme) {
            setDayNight(0);
        } else {
            setDayNight(1);
        }
        return super.onOptionsItemSelected(item);
    }
}