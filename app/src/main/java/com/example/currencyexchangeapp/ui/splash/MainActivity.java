package com.example.currencyexchangeapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import com.example.currencyexchangeapp.R;
import com.example.currencyexchangeapp.ui.home.HomeActivity;

/**
 * clase que servirá para ejecutar el splash screen
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // instalamos el splash
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * vamos a mostrar el splash solo hasta que cargue la vista, despues
         * redirigiremos al usuario al home
         */
        splashScreen.setKeepOnScreenCondition(() -> true );
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}