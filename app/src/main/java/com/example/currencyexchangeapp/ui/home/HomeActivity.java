package com.example.currencyexchangeapp.ui.home;

import static java.util.Objects.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.currencyexchangeapp.R;
//import com.example.currencyexchangeapp.databinding.ActivityHomeBinding;
import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.databinding.ActivityHomeBinding;
import com.example.currencyexchangeapp.ui.common.CurrencyAdapter;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private ExtendedCurrency currencyPais1, currencyPais2;
    private String rate1, rate2;

    // esto es para el viewBinding
    private ActivityHomeBinding binding;

    // atributos para el archivo sharedpreference
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // picker currency
    private CurrencyPicker picker;

    // viewmodel
    HomeViewModel viewModel;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // conectamos el view binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        // inicializamos el currency picker
        picker = CurrencyPicker.newInstance("Select Currency");  // dialog title

        // colocamos el tema
        setDayNight();

        setFlagDefault();

        currencyPais1 = ExtendedCurrency.getCurrencyByName("Mexico Peso");
        currencyPais2 = ExtendedCurrency.getCurrencyByName("United States Dollar");
        rate1 = "0";
        rate2 = "0";

    }

    @SuppressLint("ResourceType")
    private void setFlagDefault() {
        binding.btnPais1.setIconResource(2131165344);
        binding.btnPais2.setIconResource(2131165376);
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
                // evento al seleccionar una divisa del currency picker
                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        // Implement your code here
                        currencyPais1 = new ExtendedCurrency();
                        currencyPais1.setName(name);
                        currencyPais1.setCode(code);
                        currencyPais1.setSymbol(symbol);
                        currencyPais1.setFlag(flagDrawableResID);

                        viewModel.setFlag1(currencyPais1);

                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
            }
        });

        binding.btnPais2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // evento al seleccionar una divisa del currency picker
                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        // Implement your code here
                        currencyPais2 = new ExtendedCurrency();
                        currencyPais2.setName(name);
                        currencyPais2.setCode(code);
                        currencyPais2.setSymbol(symbol);
                        currencyPais2.setFlag(flagDrawableResID);

                        viewModel.setFlag2(currencyPais2);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
            }
        });

        binding.btnArrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revertCountry();
                revertRates();
            }
        });

        binding.btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double mount = 0.0;
                String from = "";
                String to = "";
                String mountString = binding.inputAmount.getText().toString();
                if (!mountString.isEmpty()) {
                    mount = Double.parseDouble(mountString);
                    from = currencyPais1.getCode();
                    to = currencyPais2.getCode();
                }

                // convertimos nuestra divisa a la moneda seleccionada
                viewModel.converterCurrency(from, to, mount, "json");
                // consultamos el tipo de cambio de all las divisas
                viewModel.getAllRates(from, mount, "json");
            }
        });
    }

    private void revertCountry() {

        drawBtnPais1(currencyPais2.getCode(), currencyPais2.getFlag());
        drawBtnPais2(currencyPais1.getCode(), currencyPais1.getFlag());

        ExtendedCurrency aux = currencyPais2;
        currencyPais2 = currencyPais1;
        currencyPais1 = aux;
    }

    private void revertRates(){
        rate1 = String.valueOf(binding.inputAmount.getText());
        rate2 = (String) binding.etValor2.getText();

        if (rate2.equals("0") || rate1.isEmpty())
            return;

        String from = currencyPais1.getCode();
        String to = currencyPais2.getCode();
        double newMount = Double.parseDouble(rate1);

        viewModel.converterCurrency(from, to, newMount, "json");
        viewModel.getAllRates(from, newMount, "json");
    }

    private void drawBtnPais1(String code, int flag) {
        binding.btnPais1.setText(code);
        binding.btnPais1.setIconResource(flag);
    }

    private void drawBtnPais2(String code, int flag) {
        binding.btnPais2.setText(code);
        binding.btnPais2.setIconResource(flag);
    }

    @SuppressLint("SetTextI18n")
    private void initObserver() {
        viewModel.getFlag1().observe(this, flag1 -> {
            drawBtnPais1(flag1.getCode(), flag1.getFlag());
            currencyPais1 = flag1;
        });

        viewModel.getFlag2().observe(this, flag2 -> {
            drawBtnPais2(flag2.getCode(), flag2.getFlag());
            currencyPais2 = flag2;
        });

        viewModel.getRateC().observe(this, rate -> {
            binding.etValor2.setText(rate.getRate_for_amount());
        });

        viewModel.getRates().observe(this, this::initRecyclerView);

       /*viewModel.getConversion().observe(this, conversion -> {
            binding.etValor2.setText(conversion.getRates().getRate_for_amount().toString());
        });*/
    }

    private void updateTheme(int num_theme) {
        editor.putInt("Theme", num_theme);
        editor.apply();
        setDayNight();
    }

    private void setDayNight() {
        // necesitamos obtener la configuracion en un archivo clave-valor

        int theme = sp.getInt("Theme", 0);

        if(theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES); // dark
            binding.switchTheme.setChecked(true);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO); // light
            binding.switchTheme.setChecked(false);
        }
    }

    /**
     * método para inicializar el recycler view cada que se muestre esta vista
     * @param lista
     */
    private void initRecyclerView(List<Rate> lista) {
        // colocamos un layout, en este caso el layout es lineal
        binding.currencyRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        // asignamos un adaptador al recycler view
        binding.currencyRecyclerView.setAdapter(new CurrencyAdapter(lista));
    }

    /**
     * método para recuperar el item seleccionado del recycler view por el usuario
     */
    private void onItemSelected(Rate rate) {
        // logica al momento de dar clic a uno de los item del recycler view
    }
}