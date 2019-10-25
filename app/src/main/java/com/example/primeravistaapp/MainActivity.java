package com.example.primeravistaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void lanzarInvitarAmigos(View view){
        Intent i = new Intent(this, invitarAmigos.class);
        startActivity(i);
    }

    public void lanzarLogin(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    public void lanzarEditarPerfil(View view){
        Intent i = new Intent(this, UsuarioActivity.class);
        startActivity(i);
    }

}
