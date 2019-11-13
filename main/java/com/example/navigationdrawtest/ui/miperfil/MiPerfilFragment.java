package com.example.navigationdrawtest.ui.miperfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigationdrawtest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiPerfilFragment extends Fragment {

    private miPerfilViewModel miPerfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        miPerfilViewModel =
                ViewModelProviders.of(this).get(miPerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_usuario, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        TextView nombre = (TextView) root.findViewById(R.id.nombre);
        nombre.setText(usuario.getDisplayName());
        TextView email = (TextView) root.findViewById(R.id.email);
        email.setText(usuario.getEmail());
        /*miPerfilViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}