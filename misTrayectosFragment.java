package com.example.navigationdrawtest.ui.misTrayectos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawtest.AdaptadorTrayectos;
import com.example.navigationdrawtest.R;
import com.example.navigationdrawtest.Trayectos;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class misTrayectosFragment extends Fragment{

    private misTrayectosViewModel misTrayectosViewModel;
    private FirestoreRecyclerAdapter adaptador;
    private FirebaseFirestore mFirestore;
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView reciclador;
    LinearLayoutManager layoutManager;

    public misTrayectosFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        misTrayectosViewModel =
                ViewModelProviders.of(this).get(misTrayectosViewModel.class);


        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
         reciclador = (RecyclerView) view.findViewById(R.id.recyclerViewTrayectos);
        layoutManager = new LinearLayoutManager(getContext());
        reciclador.setLayoutManager(layoutManager);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        reciclador.setLayoutManager(layoutManager);
        mFirestore = FirebaseFirestore.getInstance();
        Query query= mFirestore.collection("Trayectos").whereEqualTo("Email", usuario.getEmail())
                .orderBy("Tiempo",Query.Direction.DESCENDING).limit(50);
        FirestoreRecyclerOptions<Trayectos> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<Trayectos>()
                .setQuery(query, Trayectos.class).build();
        adaptador = new AdaptadorTrayectos(getContext(),firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        reciclador.setAdapter(adaptador);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(reciclador.getContext(), layoutManager.getOrientation());
        reciclador.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adaptador.stopListening();
    }
}