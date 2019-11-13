package com.example.primeravistaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTabHost;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;
    private static final long MIN_TIME = 0;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this,
                getSupportFragmentManager(),android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Mapa"),
                Tab1.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Trayectos"),
                Tab2.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Invitar Amigos"),
                Tab3.class, null);
        //tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Acerca de"),
        //      Tab4.class, null);
        //Intent intencion = new Intent(getApplicationContext(), MenuHamburguesa.class);
        //startActivity(intencion);

        /*btn = findViewById(R.id.BotonMapa);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {*/
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

                } else {
                    iniciarLocalizacion();
                }
            /*}
        });*/





    }

    private void iniciarLocalizacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Localizacion local = new Localizacion();


        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, 0, local);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, local);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]grantResults) {
        if(requestCode == 1000) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarLocalizacion();
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_login:
                lanzarLogin(null);
                return true;
            case R.id.menu_usuario1:
                lanzarEditarPerfil(null);
                return true;
            case R.id.BotonMapa:
                verMapa(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void verMapa(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void lanzarInvitarAmigos(View view){
        Intent i = new Intent(this, invitarAmigos.class);
        startActivity(i);
    }

    public void lanzarLogin(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        //findViewById(R.id.menu_usuario1).setVisibility(View.VISIBLE);
    }
    public void lanzarEditarPerfil(View view){
        Intent i = new Intent(this, UsuarioActivity.class);
        startActivity(i);
    }

}
