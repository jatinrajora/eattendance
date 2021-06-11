package com.demo.eattendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class teacher_attendanceSheet extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ListView listView;
    String teacher_id,class_selected;
    EditText date;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;

    String required_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_sheet);

        listView = (ListView) findViewById(R.id.list);
        date = (EditText) findViewById(R.id.date);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setTitle("Previous Record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
    }

    public void viewlist(View v) {

        Userlist.clear();
        dbStudent = ref.child("Student");
        dbStudent.orderByChild("classes").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list
                }
                display_list(Userlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void display_list(final ArrayList userlist) {

        Studentlist.clear();
        required_date = date.getText().toString();
        dbAttendance = ref.child("attendance");
        Studentlist.add("SID               " + "                         period");
        for (Object sid : userlist) {
            dbAttendance.child(required_date).child(sid.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Result will be holded Here
                    // DataSnapshot dsp=dataSnapshot.child(sid.toString());
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        String p1 = dsp.getValue().toString();
                        // Toast.makeText(getApplicationContext(),p1,Toast.LENGTH_LONG).show();
                        if ((p1.equals("A / " + teacher_id)) || (p1.equals("P / " + teacher_id))) {
                            Studentlist.add(dataSnapshot.getKey().toString() + "                 " + p1.substring(0, 1) + "                 " + dsp.getKey());
                        }
                        /* String p2=dataSnapshot.child(sid.toString()).child("p2").getValue().toString().substring(0,1);
                        String p3=dataSnapshot.child(sid.toString()).child("p3").getValue().toString().substring(0,1);
                        String p4=dataSnapshot.child(sid.toString()).child("p4").getValue().toString().substring(0,1);
                        String p5=dataSnapshot.child(sid.toString()).child("p5").getValue().toString().substring(0,1);
                        String p6=dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0,1);
                        String p7=dataSnapshot.child(sid.toString()).child("p7").getValue().toString().substring(0,1);
                        String p8=dataSnapshot.child(sid.toString()).child("p8").getValue().toString().substring(0,1); */
                        // Studentlist.add(dataSnapshot.getKey().toString() + "    " + p1); //add result into array list
                        // Toast.makeText(getApplicationContext(),Studentlist.toString(), Toast.LENGTH_LONG).show();
                        list(Studentlist);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void list(ArrayList studentlist){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_colour, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void pickDate(View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateString = format.format(c.getTime());
        date.setText(currentDateString);
    }
}
