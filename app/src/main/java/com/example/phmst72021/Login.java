package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    EditText userName, password;
    RadioGroup type;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        type = (RadioGroup) findViewById(R.id.radiogroup);
    }
    public void close(){
        this.finish();
    }
    public void onSubmit(View view){
        int selectedId;
        selectedId= type.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Detail> details = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("userName").getValue(String.class);
                    String pass = child.child("password").getValue(String.class);
                    String role = child.child("role").getValue(String.class);
                    if(name.equals(userName.getText().toString()) &&
                            role.equals(radioButton.getText().toString()) &&
                            pass.equals(password.getText().toString())){
                        for(DataSnapshot detail: child.child("details").getChildren()){
                            Detail d = new Detail(detail.child("doctorName").getValue(String.class),
                                    detail.child("visitDate").getValue(String.class),
                                    detail.child("checkUpDate").getValue(String.class));
                            details.add(d);
                            System.out.println(d.doctorName);
                        }
                        System.out.println(details.size());
                        User user = new User(child.child("userName").getValue(String.class),
                                child.child("password").getValue(String.class),child.child("patientName").getValue(String.class),
                                child.child("role").getValue(String.class),child.child("gender").getValue(String.class),
                                child.child("age").getValue(String.class),child.child("weight").getValue(String.class),
                                child.child("height").getValue(String.class),details);
                        Intent intent = new Intent(view.getContext(), Home.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                        close();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onSignUpClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        close();
    }
}