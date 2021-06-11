package com.demo.eattendance;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class addteacher extends AppCompatActivity {
    EditText Tname;
    EditText Tid;
    EditText subject,Tpassword;
    String tname,tid,sub,classname,tpass;
    Spinner classes;
    Button addButton;
    DatabaseReference databaseTeacher;
    Toolbar toolbar;

    String color = "#ffff4444";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);
        getWindow().setStatusBarColor(Color.parseColor(color));

        databaseTeacher = FirebaseDatabase.getInstance().getReference("Teacher");

        Tname =  (EditText) findViewById(R.id.editText1);
        Tid =  (EditText) findViewById(R.id.editText2);
        subject =  (EditText) findViewById(R.id.editText3);
        classes = (Spinner) findViewById(R.id.spinner);
        Tpassword =  (EditText) findViewById(R.id.editText5);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add/Remove Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addTeacher(View v){
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        sub = subject.getText().toString();
        classname = classes.getSelectedItem().toString();
        tpass = Tpassword.getText().toString();

        if (!(TextUtils.isEmpty(Tid.getText().toString()))) {
            //String id = databaseTeacher.push().getKey();
            Teacher teacher =new Teacher(tname ,tid ,sub ,classname,tpass);
            databaseTeacher.child(tid).setValue(teacher);
            Toast.makeText(getApplicationContext(),"Teacher added successfully", Toast.LENGTH_LONG).show();
            finish();

        }else {
            Toast.makeText(getApplicationContext(),"fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public void removeTeacher(View v){
        if (!TextUtils.isEmpty(Tid.getText().toString())) {
            tid = Tid.getText().toString();
            databaseTeacher.child(tid).setValue(null);
            Toast.makeText(getApplicationContext(),"Teacher removed successfully", Toast.LENGTH_LONG).show();
            finish();
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
