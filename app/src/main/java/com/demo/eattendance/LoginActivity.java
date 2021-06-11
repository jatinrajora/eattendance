package com.demo.eattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText id,password;
    String item;
    String uid,upass;
    String dbpassword;
    Bundle basket;
    ProgressDialog mDialog;
    private static long back_pressed;
    String color = "#ffff4444";

    private Admin admin;
    private Teacher teacher;
    private Student student;

    Button loginButton;

    DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(Color.parseColor(color));

        //retrieving student id from firebase
        basket = new Bundle();

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.loginButton);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("Teacher");
        categories.add("Student");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        admin = new Admin();
        teacher = new Teacher();
        student = new Student();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void onButtonClick(View v) {

        uid = id.getText().toString();
        upass = password.getText().toString();
        mDialog=new ProgressDialog(this);
        mDialog.setMessage("Please Wait..." + uid);
        mDialog.setTitle("Loading");
        mDialog.show();
        basket = new Bundle();
        basket.putString("message", uid);

        dbUser = FirebaseDatabase.getInstance().getReference().child(item).child(uid);

        dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (item == "Admin") {
                        mDialog.dismiss();
                        Admin admin = dataSnapshot.getValue(Admin.class);
                        dbpassword = admin.getApass();
                        verify(dbpassword);
                    } else {
                        mDialog.dismiss();
                        if (item == "Student") {
                            Student student = dataSnapshot.getValue(Student.class);
                            dbpassword = student.getspass();
                            verify(dbpassword);
                        }
                        if (item == "Teacher") {
                            Teacher teacher = dataSnapshot.getValue(Teacher.class);
                            dbpassword = teacher.gettpass();
                            verify(dbpassword);
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong with db", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void verify(String dbpassword){
        if(uid.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (item == "Admin" && upass.equalsIgnoreCase(dbpassword)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, adminlogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if (item == "Teacher" && upass.equalsIgnoreCase(dbpassword)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, teacherlogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if (item == "Student" && upass.equalsIgnoreCase(dbpassword)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, studentlogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if(! upass.equalsIgnoreCase(dbpassword)){
            Toast.makeText(getApplicationContext(),"UserId or Password is Incorrect", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
         if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
