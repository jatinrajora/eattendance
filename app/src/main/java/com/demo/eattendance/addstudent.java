package com.demo.eattendance;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class addstudent extends AppCompatActivity {
    EditText Sname,Sid,Spassword;
    String sname,sid,classname,spass;
    Spinner classes;
    DatabaseReference databaseStudent;
    Toolbar toolbar;

    String color = "#ffff4444";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        getWindow().setStatusBarColor(Color.parseColor(color));

        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        Sname =  (EditText) findViewById(R.id.editText1);
        Sid =  (EditText) findViewById(R.id.editText2);
        classes = (Spinner) findViewById(R.id.spinner);
        Spassword = (EditText) findViewById(R.id.editText3);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add/Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                addTeacher();
            }
        });*/
    }

    public void addStudent(View v){

        if (!(TextUtils.isEmpty(Sid.getText().toString()))) {
            //String id = databaseStudent.push().getKey();
            sname = Sname.getText().toString();
            sid = Sid.getText().toString();
            classname = classes.getSelectedItem().toString();
            spass = Spassword.getText().toString();

            Student student =new Student(sname, sid, classname, spass );
            databaseStudent.child(sid).setValue(student);
            Toast.makeText(getApplicationContext(),"student added successfully", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(),"fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public void removeStudent(View v) {

        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString();
            databaseStudent.child(sid).setValue(null);
            Toast.makeText(getApplicationContext(),"student removed successfully", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(),"id cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
