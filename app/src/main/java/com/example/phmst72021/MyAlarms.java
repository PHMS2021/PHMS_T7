package com.example.phmst72021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MyAlarms extends AppCompatActivity {
    TextView a_MyAlarmsTitle;
    ImageButton a_AddBtnAlarm;

    FirebaseAuth fAuth;
    DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alarms);

        // Textviews
        a_MyAlarmsTitle = findViewById(R.id.MyAlarmsTitle);

        // Buttons
        a_AddBtnAlarm = findViewById(R.id.AddImgBtnAlarm);
        a_AddBtnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SetAlarms = new Intent(MyAlarms.this,SetAlarms.class);
                startActivity(SetAlarms);
            }
        });


    }
}