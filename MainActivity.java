package com.example.navigationdrawtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TrayectosActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser usuario;
    AdaptadorTrayectos adaptador;

    static ArrayList<Trayectos> listaDocumentos = new ArrayList<>();

    private AppBarConfiguration mAppBarConfiguration;
    private boolean flag;

    private static final long MIN_TIME = 0;

    boolean click = false;

    FloatingActionButton[] fab2 = new FloatingActionButton[5];
    FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = isUserLogged();

        usuario = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //com.github.clans.fab.FloatingActionButton fab = findViewById(R.id.floatingActionItem1);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comprobarUsuarios(1);
                //startService(new Intent(MainActivity.this, NotificacionTrayectos.class));

            }
        });*/
        descargaDocumentos();

        // variable declarada en la clase
        //FloatingActionButton[] fab2 = new FloatingActionButton[5];
        fab2[0] = (FloatingActionButton) findViewById(R.id.floatingActionItem1);
        fab2[1] = (FloatingActionButton) findViewById(R.id.floatingActionItem2);
        fab2[2] = (FloatingActionButton) findViewById(R.id.floatingActionItem3);
        fab2[3] = (FloatingActionButton) findViewById(R.id.floatingActionItem4);
        fab2[4] = (FloatingActionButton) findViewById(R.id.floatingActionItem5);

        /*for (contador = 0; contador < 5; contador++) {
            fab2[contador].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // comprobar les mac's
                    String mac[] = new String[5];
                    mac[0] = "MAC:DE:LA:BICI:1";
                    mac[1] = "MAC:DE:LA:BICI:2";
                    mac[2] = "MAC:DE:LA:BICI:3";
                    mac[3] = "MAC:DE:LA:BICI:4";
                    mac[4] = "MAC:DE:LA:BICI:5";
                    //comprobarUsuarios(mac[contador]);
                    descargaDocumentos();
                    comprobarUsuarios(contador+1);
                }
            });
        }
        contador = 0;*/
        fab2[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //descargaDocumentos();
                comprobarUsuarios(1);
            }
        });
        fab2[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //descargaDocumentos();
                comprobarUsuarios(2);
            }
        });
        fab2[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //descargaDocumentos();
                comprobarUsuarios(3);
            }
        });
        fab2[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //descargaDocumentos();
                comprobarUsuarios(4);
            }
        });
        fab2[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //descargaDocumentos();
                comprobarUsuarios(5);
            }
        });

        //cargo inicialmente los botones
        compruebaBicicletas();

        fam = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    //fam.close(true);
                    fam.open(true);
                    Log.i(TAG, "-------------> Voy a comprobar bicicletas");
                    descargaDocumentos();
                    compruebaBicicletas();
                }
            }
        });

        // per a controlar quan fa click en el FlotingActionMenu
        /*fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descargaDocumentos();
                Toast.makeText(MainActivity.this, "He hecho click en setOnMenuClickListener", Toast.LENGTH_SHORT).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        if (flag) {
            TextView name = hView.findViewById(R.id.headerName);
            name.setText(usuario.getDisplayName());
            TextView email = hView.findViewById(R.id.headerEmail);
            email.setText(usuario.getEmail());
        } else {
            TextView name = hView.findViewById(R.id.headerName);
            name.setText("Anónimo");
            TextView email = hView.findViewById(R.id.headerEmail);
            email.setText("");
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        } else {
            iniciarLocalizacion();
        }

        //descargaDocumentos();
    }

    private void descargaDocumentos() {
        final CollectionReference trayectos = db.collection("Trayectos");
        trayectos.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //borramos listadocumentos para volverlo a cargar
                            listaDocumentos.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Creamos un trayecto
                                Trayectos trayecto = new Trayectos(document.get("Email").toString(),
                                        document.get("IDbici").toString(),
                                        ((Boolean) document.get("Movimiento")),
                                        document.get("Base").toString(),
                                        (long) document.get("Tiempo"));
                                Log.i(TAG, "He introducido el trayecto " + trayecto);
                                listaDocumentos.add(trayecto);
                                Log.i(TAG, "El documento " + document.getId() + " -->" + document.getData());
                            }
                        } else {
                            Log.i(TAG, "Error obteniendo documento " + task.getException());
                        }
                    }
                });
    }

    public void iniciarTrayectos(String correo, String idBici, String base) {

        Map<String, Object> datos = new HashMap<>();
        datos.put("IDbici", idBici);
        datos.put("Email", correo);
        datos.put("Base", base);
        datos.put("Movimiento", true);
        datos.put("Tiempo", new Date().getTime());

        db.collection("Trayectos").document().set(datos);
        db.collection("Bicicletas").document(idBici).update("Disponible", false);

        startService(new Intent(MainActivity.this, NotificacionTrayectos.class));

        // cargo los documentos actualizados
        descargaDocumentos();
        // cerramos el menú
        fam.close(true);


        Toast.makeText(this, "Trayecto con la " + idBici + " empezado", Toast.LENGTH_LONG).show();
    }

    public void comprobarUsuarios(int id) {
        boolean movimiento = false;
        //descargaDocumentos();
        if (usuario == null) {
            Toast.makeText(this, "Debes iniciar sesión para crear un trayecto", Toast.LENGTH_LONG).show();
        } else {
            int posicion = 0;
            for (int i = 0; !movimiento && i < listaDocumentos.size(); i++) {
                Log.i(TAG, "--------------------------> email-doc: " + listaDocumentos.get(i).getEmail() + " user-email: " + usuario.getEmail());
                if (listaDocumentos.get(i).getEmail().equals(usuario.getEmail()) && listaDocumentos.get(i).isMovimiento()) {
                    movimiento = true;
                    posicion = i;
                    Log.i(TAG, "--------------------------> Dentro del if. movimiento: " + movimiento + " posicion: " + posicion);
                }
            }
            // si existe un usuario con movimiento en true
            if (movimiento) {
                Toast.makeText(MainActivity.this, "Ya tienes un trayecto activo", Toast.LENGTH_LONG).show();
            } else {
                //bici 1 debe ser la MAC
                Log.i(TAG, "Lo creo porque MOVIMIENTO es false");
                iniciarTrayectos(usuario.getEmail(), "Bici " + String.valueOf(id), "1_9");
                // quito la bicicleta del menú
                fab2[id - 1].setVisibility(View.GONE);
            }
        }
    }

    private void compruebaBicicletas() {
        final CollectionReference bicicletas = db.collection("Bicicletas");
        bicicletas.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // oculto todos los botones
                            for (int i = 0; i < 5; i++) {
                                fab2[i].setVisibility(View.GONE);
                            }
                            // muestra sólo los que estén disponibles
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("ID").equals("Bici 1") && document.getBoolean("Disponible"))
                                    fab2[0].setVisibility(View.VISIBLE);
                                if (document.get("ID").equals("Bici 2") && document.getBoolean("Disponible"))
                                    fab2[1].setVisibility(View.VISIBLE);
                                if (document.get("ID").equals("Bici 3") && document.getBoolean("Disponible"))
                                    fab2[2].setVisibility(View.VISIBLE);
                                if (document.get("ID").equals("Bici 4") && document.getBoolean("Disponible"))
                                    fab2[3].setVisibility(View.VISIBLE);
                                if (document.get("ID").equals("Bici 5") && document.getBoolean("Disponible"))
                                    fab2[4].setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.i(TAG, "Error obteniendo documento " + task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item1 = menu.findItem(R.id.menu_login);
        MenuItem item2 = menu.findItem(R.id.menu_usuario1);
        if (flag) {
            item1.setVisible(false);
            item2.setVisible(false);
        } else {
            item2.setVisible(false);
            item1.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_login:
                lanzarLogin(null);
                return true;
            case R.id.action_settings:
                lanzarNormas(null);
                return true;
            case R.id.boton_mapa:
                lanzarMapa(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void lanzarMapa(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static boolean isUserLogged() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return true;
        }
        return false;
    }

    public void lanzarLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void lanzarNormas(View view) {
        Intent i = new Intent(this, NormasActvity.class);
        startActivity(i);
    }

    public void iniciarLocalizacion() {
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarLocalizacion();
                return;
            }
        }
    }

    public void lanzarInvitarAmigos(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Te invito a que descargues la APP de bicicletas: https://drive.google.com/file/d/1o7wEyF8kxrmyB43lCD4-0608xYL6rAFU/view?usp=sharing");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void llamarTelefono(MenuItem view) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:962849347"));
        startActivity(intent);
    }
}





