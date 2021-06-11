package com.demo.eattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class adminlogin extends AppCompatActivity {

    String uid,upass;
    String aid;
    DatabaseReference ref;
    DatabaseReference dbStudent;
    DatabaseReference dbAttendance;
    DatabaseReference dbadmin;
    Toolbar toolbar;
    private static long back_pressed;
    Admin admin;

    String color = "#ffff4444";

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        getWindow().setStatusBarColor(Color.parseColor(color));
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Dashboard : "+"("+date+")");
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student");
        dbAttendance = ref.child("attendance");
        admin = new Admin();
    }

    public void AddTeacherButton(View v){
        Intent intent = new Intent(this, addteacher.class);
        startActivity(intent);
    }

    public void AddStudentButton(View v){
        Intent intent = new Intent(this, addstudent.class);
        startActivity(intent);
    }

    public void CreateAttendance(View v){

        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sid,P1="-",P2="-",P3="-",P4="-",P5="-",P6="-",P7="-",P8="-";
                Attendance_sheet a = new Attendance_sheet(P1,P2,P3,P4,P5,P6,P7,P8);
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    sid = dsp.child("sid").getValue().toString(); //add result into array list
                    dbAttendance.child(date).child(sid).setValue(a);
                }
                Toast.makeText(getApplicationContext(),"Successfully created "+date+" db", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        });

    }

    public void attendanceRecord(View v){
        Intent intent = new Intent(this, admin_attendanceSheet.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent logout = new Intent(adminlogin.this,LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);
    }

    public void changepassword(View view) {

        dbadmin = ref.child("Admin");

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Type your new password");
        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.changepassword, null);
        final EditText id=(EditText)add_menu_layout.findViewById(R.id.id);
        final EditText password=(EditText)add_menu_layout.findViewById(R.id.newpassword);
        alertDialog.setView(add_menu_layout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                if (!TextUtils.isEmpty(password.getText().toString()))
                {
                    uid = id.getText().toString();
                    upass = password.getText().toString();
                    dbadmin.orderByChild("aid").equalTo(id.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                aid = ds.child("aid").getValue(String.class);
                                Admin admin = ds.getValue(Admin.class);
                                if(aid.equals(uid)){
                                    admin.setAid(id.getText().toString());
                                    admin.setApass(password.getText().toString());
                                    dbadmin.child(uid).setValue(admin);
                                    Toast.makeText(adminlogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(adminlogin.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(adminlogin.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
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
