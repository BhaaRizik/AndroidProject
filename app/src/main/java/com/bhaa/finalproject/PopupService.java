package com.bhaa.finalproject;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopupService extends Service {

    final String tag="finalProject.bhaa";
    LessonDetails lessonDetails;
   // FirebaseDatabase database;
    public static DatabaseReference RequestRef;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    DatabaseReference usersdRef = rootRef.child("users");




  /* usersdRef.addListenerForSingleValueEvent(new ValueEventListener() {
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                String email = ds.child("email").getValue(String.class);
                Log.i(tag,email);

            }
           // ArrayAdapter<String> adapter = new ArrayAdapter(OtherUsersActivity.this, android.R.layout.simple_list_item_1, array);

         //   mListView.setAdapter(adapter);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
  ///  usersdRef.addListenerForSingleValueEvent(eventListener);
}*/

    @Override
    public void onCreate()
    {
        super.onCreate();
        lessonDetails=new LessonDetails();
        Log.i(tag,"Service");
       // android.os.Debug.waitForDebugger();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // database = FirebaseDatabase.getInstance();
        RequestRef = FirebaseDatabase.getInstance().getReference("lessons");
        ListenerForRequestDone();
        Log.i(tag,"onStartCommand");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("RESTART_SERVICE");
        sendBroadcast(intent);
    }


    public void ListenerForRequestDone(){

        usersdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String email = ds.child("email").getValue(String.class);
                    Log.i(tag,email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        RequestRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              //  if (lessonDetails.getUser().getEmail().equals("bhaarizik95@gmail.com")) {
                    Toast.makeText(PopupService.this, "onChildAdded", Toast.LENGTH_SHORT).show();
                    Log.i(tag, "onChildAdded :)");
              //  }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //   Toast.makeText(LessonDetails.this,"onChildChanged",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
