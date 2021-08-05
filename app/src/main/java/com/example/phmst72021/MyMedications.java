package com.example.phmst72021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MyMedications extends AppCompatActivity {

    TextView a_MyMedsTitle;
    ImageButton a_AddBtn;

    FirebaseAuth fAuth;
    DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medications);

        a_MyMedsTitle = findViewById(R.id.MyMedsTitle);

        a_AddBtn = findViewById(R.id.AddImgBtn);
        a_AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddMeds = new Intent(MyMedications.this,AddMeds.class);
                startActivity(AddMeds);
                //finish();
            }
        });
    }
}