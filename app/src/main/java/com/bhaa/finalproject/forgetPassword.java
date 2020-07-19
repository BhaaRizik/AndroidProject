package com.bhaa.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class forgetPassword extends AppCompatDialogFragment {

    public ResetDialogListener listener;
    EditText emailToReset;
    final String tag="finalProject.bhaa";
    String email =" ";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.forget_password, null);
        builder.setView(view)
                .setTitle("Reset Password")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(tag,"---"+email);
                        email=emailToReset.getText().toString().trim();
                        Log.i(tag,"---"+email);
                        try {
                            listener.applyUpdate(email);
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.i(tag,e.getMessage());
                        }
                    }
                });
        emailToReset= view.findViewById(R.id.emailToReset);
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ResetDialogListener) context;
    }
    public interface ResetDialogListener {
        void applyUpdate(String Email);
    }
}
