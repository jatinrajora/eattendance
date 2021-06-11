package com.demo.eattendance;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student_attendance_sheet extends AppCompatActivity {

    public static int TOC=0,NOP=0,NOA=0;
    float average= (float) 0.0;
    TextView t;
    String p1,p2,p3,p4,p5,p6,p7,p8;
    String student_id;
    ArrayList dates = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_sheet2);

        Toolbar mToolbar = findViewById(R.id.studentsheet);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Student Record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        t=(TextView) findViewById(R.id.textView3);

        listView = (ListView) findViewById(R.id.list);
        Bundle bundle = getIntent().getExtras();
        student_id = bundle.getString("sid");
        t.setText(student_id);

        dates.clear();
        dates.add("Date                "+"p1  "+"p2  "+"p3  "+"p4   "+ "p5   "+"p6  "+"p7  "+"p8");

        dbAttendance = ref.child("attendance");
        dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    if(dsp.child(student_id).child("p1").getValue() != null){
                        p1 = dsp.child(student_id).child("p1").getValue().toString().substring(0, 1);
                        if(p1.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p2").getValue() != null){
                        p2 = dsp.child(student_id).child("p2").getValue().toString().substring(0, 1);
                        if(p2.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p3").getValue() != null){
                        p3 = dsp.child(student_id).child("p3").getValue().toString().substring(0, 1);
                        if(p3.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p4").getValue() != null){
                        p4 = dsp.child(student_id).child("p4").getValue().toString().substring(0, 1);
                        if(p4.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p5").getValue() != null){
                        p5 = dsp.child(student_id).child("p5").getValue().toString().substring(0, 1);
                        if(p5.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p6").getValue() != null){
                        p6 = dsp.child(student_id).child("p6").getValue().toString().substring(0, 1);
                        if(p6.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p7").getValue() != null){
                        p7 = dsp.child(student_id).child("p7").getValue().toString().substring(0, 1);
                        if(p7.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }
                    if(dsp.child(student_id).child("p8").getValue() != null){
                        p8 = dsp.child(student_id).child("p8").getValue().toString().substring(0, 1);
                        if(p8.equals("P")) {
                            NOP++;
                        } else {
                            NOA++;
                        }
                    }

                    dates.add(dsp.getKey() + "    " + p1 + "     " + p2 + "     " + p3 + "     " + p4 + "      " + p5 + "       " + p6 + "      " + p7 + "      " + p8); //add result into array list


                    //  Toast.makeText(getApplicationContext(),dsp.child(student_id).child("p1").getValue().toString(),Toast.LENGTH_LONG).show();
                    /*if (p1.equals("P")||p2.equals("P")||p3.equals("P")||p4.equals("P")) {
                        NOP++;
                        TOC++;
                    }
                    if(p1.equals("A")||p2.equals("A")||p3.equals("A")||p4.equals("A")) {
                        NOA++;
                        TOC++;
                    }*/
                }
                list(dates,NOP,TOC,NOA);
                // Toast.makeText(getApplicationContext(), dates.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void list(ArrayList studentlist,int NOP,int TOC,int NOA) {
        // Toast.makeText(this,NOP+TOC,Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_colour, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        //t.setText("Your total present is " + NOP);
        try {
            average = (float) ((NOP * 100) / TOC);
            String avg = Float.toString(average);
            t.setText("Your Attendance is :" + avg + "%");
            if (average >= 75)
                t.setTextColor(Color.GREEN);
            if (average < 75)
                t.setTextColor(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
