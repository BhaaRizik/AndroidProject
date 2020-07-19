package com.bhaa.finalproject;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class LessonDetails extends AppCompatActivity implements updateDialog.UpdateDialogListener,
        showProfile.UpdateDialogListener, AddLesson.UpdateDialogListener {
    final String tag="finalProject.bhaa";
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;



    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private DatabaseReference changesInDatabase;
    private FirebaseAuth firebaseAuth;
    private CollectionReference lessonRef ;
    private FirebaseFirestore db ;
    TextView fullName;
    String name;
    TextView email;
    TextView wel,test;

    FloatingActionButton floatingActionButton;

    Context lessonDetails;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }
        user=firebaseAuth.getCurrentUser();
        if(user!=null){
            if(user.getEmail().equals("bhaarizik95@gmail.com")){
                setContentView(R.layout.activity_lesson_details);
            }else{
                setContentView(R.layout.lesson_details_for_students);
                name=user.getDisplayName();
                Log.i(tag,"name= "+name);
            }
        }


        mDatabase = FirebaseDatabase.getInstance().getReference("lessons");
        changesInDatabase = FirebaseDatabase.getInstance().getReference("lessons");


        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        floatingActionButton=findViewById(R.id.addLesson);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LessonDetails.this,"Add Lesson",Toast.LENGTH_SHORT).show();
                OpenAddLesson();
            }
        });
       db = FirebaseFirestore.getInstance();
       lessonRef = db.collection("lessons");

        notificationManager = NotificationManagerCompat.from(this);

        createNotificationChannels();
        lessonDetails=this;
    }


    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }


//-------------------------- Menu ----------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.showprofile:
                Toast.makeText(this, "show profile", Toast.LENGTH_SHORT).show();
                showMyProfile();
                return true;
            case R.id.updateprofile:
                Toast.makeText(this, "update profile", Toast.LENGTH_SHORT).show();
                openUpdateDialog();
                return true;

            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LessonDetails.this,settingAvticity.class));
                return true;
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void OpenAddLesson() {


        AddLesson Dialog = new AddLesson();
        Dialog.show(getSupportFragmentManager(), "addLesson dialog");

    }

    private void showMyProfile() {

        showProfile exampleDialog = new showProfile();
        exampleDialog.show(getSupportFragmentManager(), "show dialog");
    }

    void openUpdateDialog(){
        updateDialog exampleDialog = new updateDialog();
        exampleDialog.show(getSupportFragmentManager(), "Update dialog");
    }

//------------------------ Update Profile ---------------------------------------------------------
    @Override
    public void applyUpdate(String fullName) {
     //   test.setText(fullName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(tag, "User profile updated.");
                        }
                    }
                });

    }

//------------------------------------------------------------------------------------------------
    @Override
    public void showProfile(String username) {

    }

//-------------------------- Add Lesson to Firebase -----------------------------------------------


    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
    public void sendOnChannel1(String Title,String Content) {
        String title = Title;
        String message = Content;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.buttonshape)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }




    ArrayList<Lesson> existLesson=new ArrayList<>();
    List<String> keys = new ArrayList<>();
    int counter=0;
public void getLessons(DataSnapshot dataSnapshot){

        //insert the lessons to "existLesson" arrayList
        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                keys.add(keyNode.getKey());
                Lesson lesson = keyNode.getValue(Lesson.class);
                existLesson.add(lesson);
          //      Log.i(tag, "data : " + lesson.getSubject());
        }//for


}


    int flag=1;
    @Override
    public void addLesson(final String subject, final String topic, final String date, final String time) {

        flag=1;
        mDatabase.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              /*
           if(dataSnapshot.exists()) {
                    Toast.makeText(LessonDetails.this,"date exist",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(tag," else");
                    String id=mDatabase.push().getKey();
                    Lesson lesson=new Lesson(subject,topic,date,time,id); //create lesson
                    Toast.makeText(LessonDetails.this,subject+" - "+topic+" - "+date+" - "+time,Toast.LENGTH_SHORT).show();

                    mDatabase.child(id).setValue(lesson);//add lesson to DB
                }
///////////////////////////////////////////////////////////////////////////////////////////////////
 */

                    getLessons(dataSnapshot);

                    //Check if date and time is busy
                    for (Lesson lessonToCheck : existLesson) {
                     //   Log.i(tag, "))) " + lessonToCheck.getDate() + " , " + date);
                       // Log.i(tag, "((( " + lessonToCheck.getTime() + " , " + time);
                        if (lessonToCheck.getDate().equals(date) && lessonToCheck.getTime().equals(time)) {

                            flag = 0;
                        }
                      //  Log.i(tag, "flag= "+flag);
                    }//for

                    if (flag == 0) {
                        Toast.makeText(LessonDetails.this, "date exist", Toast.LENGTH_SHORT).show();
                      //  Log.i(tag, " date exist");
                        // Check empty lessons
                        nearestLessons(existLesson, date, time);
                    } else {
                        if (flag == 1) {
                            new AlertDialog.Builder(lessonDetails)
                                    .setTitle("Lesson Confirmation")
                                    .setMessage("Lesson details:\nDate: "+date+"\nTime: "+time)

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                          //  Log.i(tag, " else");
                                            String id = mDatabase.push().getKey();
                                        //    Log.i(tag, "id = " + id);
                                            Lesson lesson = new Lesson(subject, topic, date, time, id,user.getDisplayName()); //create lesson
                                            Toast.makeText(LessonDetails.this,
                                                    subject + " - " + topic + " - " + date + " - " + time, Toast.LENGTH_SHORT).show();
                                        //    Log.i(tag, subject + " - " + topic + " - " + date + " - " + time);

                                              mDatabase.child(id).setValue(lesson);

                                            //send notification to the admin

                                            Log.i(tag,"ah ha ah");
                                           // registerReceiver(new ReceiverCall(),new Intent(LessonDetails.this, PopupService.class));
                                           // sendBroadcast(new Intent(LessonDetails.this, PopupService.class));
                                           startService(new Intent(LessonDetails.this, PopupService.class));
                                            Log.i(tag,"58 85");
                                            if (sharedPreferences.getBoolean("noti",true)) {
                                                sendOnChannel1(subject, date + " at: " + time);
                                            }

                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton("Cancel", null)
                                    .setIcon(android.R.drawable.ic_menu_view)
                                    .show();

                        } //add lesson to DB
                    } //else

         Log.i(tag,"end");
            } //onDataChange




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

//------------------------- Find nearest Date ----------------------------------------------------------------

    ArrayList<Lesson> lessonsInSpecificDate =new ArrayList<>();

public void nearestLessons(ArrayList<Lesson> lessons,String date,String time){


    if(date.equals("") || time.equals("")){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Enter date and time ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
else {
        int flag = 0;
        String[] timeSplit = time.split(":");
        int time_In_Int = Integer.parseInt(timeSplit[0]); ///takes the hours
        int minDistanceBetweenLessons = 21;
        int timeInHours = 8;
        int nearestTime = 0;

        for (Lesson lesson : lessons) {
            if (lesson.getDate().equals(date)) {
                lessonsInSpecificDate.add(lesson);//collect all lessons in same date
            }
        }//for

        while (timeInHours < 21) {
            for (Lesson lesson : lessonsInSpecificDate) {
                if (lesson.getTime().equals(timeInHours + ":00")) {// if the time exist , so search the next tme
                  //  Log.i(tag, "if= " + timeInHours);
                    timeInHours++;
                }
            }//for
            Log.i(tag, "_= " + timeInHours);
            if (minDistanceBetweenLessons >= Math.abs(time_In_Int - timeInHours) && Math.abs(time_In_Int - timeInHours) != 0) {
                minDistanceBetweenLessons = Math.abs(time_In_Int - timeInHours);
                nearestTime = timeInHours;
            }
            timeInHours++;
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("The nearest hour to your choice is : " + nearestTime + ":00");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
      //  Log.i(tag, "Nearest Time: " + nearestTime);
    }
}


//--------------------------View all Lessons in RecyclerView---------------------------------------


    @Override
    protected void onStart() {
        super.onStart();
        recyclerView=(RecyclerView)findViewById(R.id.listOfLessons);

        Query query = lessonRef.orderBy("date", Query.Direction.DESCENDING);
        final FirestoreRecyclerOptions<Lesson> options = new FirestoreRecyclerOptions.Builder<Lesson>()
                .setQuery(query,Lesson.class)
                .build();


            upload(new lessonDetails() {
                @Override
                public void LessonIsLoad(List<Lesson> lessons, List<String> keys) {
                    new RecyclerView_show_Lessons().
                            setConfig(recyclerView, LessonDetails.this, lessons, keys, options, mDatabase);
                }
            });

    }

    public interface lessonDetails{
        void LessonIsLoad(List<Lesson> lessons,List<String> keys);

    }

    ArrayList<Lesson> lessons=new ArrayList<>();

    RecyclerView recyclerView;
   /* public void showAllLessons(View view) {

        recyclerView=(RecyclerView)findViewById(R.id.listOfLessons);

        Query query = lessonRef.orderBy("date", Query.Direction.DESCENDING);
        final FirestoreRecyclerOptions<Lesson> options = new FirestoreRecyclerOptions.Builder<Lesson>()
                .setQuery(query,Lesson.class)
                .build();

        upload(new lessonDetails() {

            @Override
            public void LessonIsLoad(List<Lesson> lessons, List<String> keys) {
                new RecyclerView_show_Lessons().
                        setConfig(recyclerView,LessonDetails.this,lessons,keys,options,mDatabase);
            }
        });

    }*/


    public void upload(final lessonDetails lessonDetail){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lessons.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Lesson lesson=keyNode.getValue(Lesson.class);
                    if(user.getEmail().equals("bhaarizik95@gmail.com")) {
                        lessons.add(lesson);
                    }else{
                        if (lesson.getUserName().equals(user.getDisplayName())){
                            lessons.add(lesson);
                        }
                    }
                    //Log.i(tag,"data : "+lesson.getSubject());
                }
              //  Log.i(tag,"lessons.size() "+lessons.size());
                lessonDetail.LessonIsLoad(lessons,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//-------------------------------------------------------------------------------------------------

}
