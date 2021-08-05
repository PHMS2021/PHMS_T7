package com.example.phmst72021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Medication extends AppCompatActivity {

    TextView a_MedsTitle, a_AddEditMedTitle, a_AddEditAlarmsTitle;
    ImageButton a_MedsImgBtn, a_AlarmsImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        a_MedsTitle = findViewById(R.id.MedsIntakeTitle);
        a_AddEditMedTitle = findViewById(R.id.MedsAddEditTitle);
        a_AddEditAlarmsTitle = findViewById(R.id.AlarmsAddEditTitle);

        a_MedsImgBtn = findViewById(R.id.MedsImgBtn);
        a_AlarmsImgBtn = findViewById(R.id.AlarmsImgBtn);

        a_MedsImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyMeds = new Intent(Medication.this,MyMedications.class);
                startActivity(MyMeds);
                //finish();
            }
        });

        a_AlarmsImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAlarms = new Intent(Medication.this,MyAlarms.class);
                startActivity(MyAlarms);
                //finish();
            }
        });
    }
}