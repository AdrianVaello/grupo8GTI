package com.example.navigationdrawtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.navigationdrawtest.ui.misTrayectos.misTrayectosFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class AdaptadorTrayectos extends FirestoreRecyclerAdapter<Trayectos, AdaptadorTrayectos.ViewHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TrayectosActivity";
    //ArrayList<Trayectos> listaTrayectosUsuario = new ArrayList<>();
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    private Context context;
    protected View.OnClickListener onClickListener;
    FirestoreRecyclerOptions<Trayectos> options;

    public AdaptadorTrayectos(Context context, @NonNull FirestoreRecyclerOptions<Trayectos> options) {
        super(options);
        this.context = context.getApplicationContext();
        this.options = options;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public final TextView emailUsuario;
        public final TextView idBici;
        public final TextView tiempo;
        public final ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);
            this.emailUsuario = (TextView) itemView.findViewById(R.id.emailUsuario);
            this.idBici = (TextView) itemView.findViewById(R.id.idBici);
            this.tiempo = (TextView) itemView.findViewById(R.id.Tiempo);
            imagen = (ImageView) itemView.findViewById(R.id.miniatura_bici);
        }
    }

   /* @Override
    public int getItemCount() {
        return listaTrayectosUsuario.size();
    }*/


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.elemento_lista_trayectos, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Trayectos model) {
        //Log.i(TAG, "Model : " + model);
        //Log.i(TAG, "options: " + options.getSnapshots());
        holder.emailUsuario.setText("Email: " + options.getSnapshots().getSnapshot(position).get("Email").toString());
        holder.idBici.setText("Id de la bici: " + options.getSnapshots().getSnapshot(position).get("IDbici").toString());


        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        Date date = new Date(Long.parseLong(String.valueOf(options.getSnapshots().getSnapshot(position).get("Tiempo"))));
        String cadenaFecha = simpleDateFormat.format(date);
        holder.tiempo.setText(cadenaFecha);

    }


    public void setOnItemClickListener(View.OnClickListener onClick) {
        onClickListener = onClick;
    }

}
