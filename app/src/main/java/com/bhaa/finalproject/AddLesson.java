package com.bhaa.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import com.bhaa.finalproject.R;

public class AddLesson extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    final String tag="finalProject.bhaa";

    EditText subject;
    EditText topic;
    EditText date;
    EditText time;
    TextView dateText;
    Button pickDate;
    TextView timeText;
    Button pickTime;

    private UpdateDialogListener listener;




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_lesson, null);

        builder.setView(view)
                .setTitle("Add Lesson")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String subjectString = subject.getText().toString();
                        String topicString = topic.getText().toString();
                        String dateString = dateText.getText().toString();
                        String timeString = timeText.getText().toString();

                        listener.addLesson(subjectString,topicString,dateString,timeString);
                    }
                });


        subject = view.findViewById(R.id.subject);
        topic = view.findViewById(R.id.topic);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);

        pickDate=view.findViewById(R.id.pickDate);
        dateText=view.findViewById(R.id.textViewDate);

        pickTime=view.findViewById(R.id.pickTime);
        timeText=view.findViewById(R.id.textviewTime);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(AddLesson.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setTargetFragment(AddLesson.this, 0);
                timePicker.show(getFragmentManager(), "time picker");
            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Log.i(tag,"currentDateString"+currentDateString);
         dateText.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeText.setText(hourOfDay + ":" + "00");


    }


    public interface UpdateDialogListener {
        void addLesson(String subject,String topic,String date,String time);
    }


}






