package com.bhaa.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Currency;

public class showProfile extends AppCompatDialogFragment {

    TextView fullName, email;
    private UpdateDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_profile, null);

        fullName = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);

        FirebaseUser user;
        FirebaseAuth firebaseAuth = null;
        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getInstance().getCurrentUser();
        fullName.setText(user.getDisplayName());
        email.setText(user.getEmail());

        builder.setView(view)
                .setTitle("My profile")
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }


    public interface UpdateDialogListener {
        void showProfile(String username);
    }

}
