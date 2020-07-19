package com.bhaa.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecyclerView_show_Lessons {
    final static String tag = "finalProject.bhaa";
    private Context context;
    List<Lesson> lessons;
    List<String> keys;
    FirestoreRecyclerOptions<Lesson> options;
    private RecyclerViewshowLessons recyclerView_show_lessons;
    public DatabaseReference lessonRef ;




    public void setConfig(RecyclerView recyclerView, Context context, List<Lesson> lessons, List<String> keys,
                          FirestoreRecyclerOptions<Lesson> options, DatabaseReference lessonRef) {
        this.context = context;

        this.lessons=lessons;
        this.keys=keys;
        this.options=options;
        this.lessonRef=lessonRef;
       // this.lessonRef= FirebaseFirestore.getInstance();
        this.recyclerView_show_lessons = new RecyclerViewshowLessons(lessons, keys,options);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recyclerView_show_lessons);


    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position= viewHolder.getAdapterPosition();
            Log.i(tag,"position= "+position);
            Lesson lessonTodelete=lessons.get(position);
            Log.i(tag,"lesonName= "+lessonTodelete.getDate());
            lessonRef.child(lessonTodelete.getID()).removeValue();
            Log.i(tag,"= "+lessonRef.child("lessons").child(lessonTodelete.getID()).getKey());
            //   Log.i(tag,"ihdsfoh");
           // RecyclerViewshowLessons recyclerViewshowLessons=new RecyclerViewshowLessons(lessons,keys,options);
         //   recyclerViewshowLessons.deleteItem((String) viewHolder.itemView.getTag(),lessonRef);
           /* Log.i(tag,"==> "+ lessonRef
                    .document(String.valueOf(viewHolder.itemView.getId())));

            lessonRef
                    .document(String.valueOf(viewHolder.itemView.getId()))
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(tag, "onSuccess: Removed list item");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(tag, "onFailure: " + e.getLocalizedMessage());
                        }
                    });*/

        }
    };
    static class RecyclerViewshowLessons extends
            FirestoreRecyclerAdapter<Lesson, subClass> /*extends RecyclerView.Adapter<subClass>*/ {

        private static List<Lesson> lessons;
        private static List<String> keys;

        public RecyclerViewshowLessons(List<Lesson> lessons, List<String> keys,
                                       @NonNull FirestoreRecyclerOptions<Lesson> options) {
            super(options);
          //  Log.i(tag, "2 ");
            this.lessons = lessons;
            this.keys = keys;
         //   Log.i(tag, "lessons.size(): " + lessons.size());
        }

        @NonNull
        @Override
        public subClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           // Log.i(tag, "1 ");

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lesson_item_recyclerview, parent, false);
         //   Log.i(tag, "11 ");
            return new subClass(v);
        }



      @Override
        public void onBindViewHolder(@NonNull subClass holder, int position) {
         // Log.i(tag, "<- ");
          holder.bind(lessons.get(position), keys.get(position), position);
        }

        @Override
        protected void onBindViewHolder(@NonNull subClass holder, int position, @NonNull Lesson model) {
          //  Log.i(tag, "-> ");
            holder.bind(lessons.get(position), keys.get(position), position);
        }

       public void deleteItem(String position, CollectionReference lessonRef) {
            Log.i(tag,"deleteItem at:"+position);
          // getSnapshots().getSnapshot(position).getReference().delete();

    }

        @Override
        public int getItemCount() {
          //  Log.i(tag,"getItemCount");
            return lessons.size();
        }


    }
    static class subClass extends RecyclerView.ViewHolder  {


        public TextView subject;
        public TextView date;
        public TextView time;
        public TextView topic;
        public TextView userName;
        String key;


        public subClass(@NonNull View itemView) {
            super(itemView);
         //   Log.i(tag, "6 ");
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            topic = itemView.findViewById(R.id.topic);
            userName = itemView.findViewById(R.id.student);
           //bhafsdfsd Log.i(tag, "66 ");

        }

            public void bind (Lesson lesson, String key,int position){
        //    Log.i(tag,"bind");
                Lesson lesson1 = RecyclerViewshowLessons.lessons.get(position);
                subject.setText("Subject: "+lesson1.getSubject());
                date.setText(lesson1.getDate());
                time.setText(lesson1.getTime());
                topic.setText("Topic: "+lesson1.getTopic());
                userName.setText("Student Name: "+lesson1.getUserName());
                this.key = key;
            }
    }


}





