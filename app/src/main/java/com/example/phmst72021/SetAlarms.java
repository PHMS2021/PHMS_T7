package com.example.phmst72021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetAlarms extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView a_AddAlarmsTitle, a_TimeSet, a_RepeatDaysAlarm, a_MedsTitleSpinner;
    Button a_SelectTimeBtn, a_SetAlarmBtn;

    FirebaseAuth fAuth;
    DatabaseReference firebaseRef;
    List<String> medNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarms);

        // Text Views
        a_AddAlarmsTitle = findViewById(R.id.AddAlarmsTitle);
        a_TimeSet = findViewById(R.id.timeSet);
        a_RepeatDaysAlarm = findViewById(R.id.RepeatDaysAlarm);
        a_MedsTitleSpinner = findViewById(R.id.MedsTitleSpinner);

        // Spinner
        Spinner a_MedsNameSpinner = findViewById(R.id.MedNameSpinner);
        medNames = new ArrayList<>();


        firebaseRef = FirebaseDatabase.getInstance().getReference("Medication");
        firebaseRef.child("User2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot childSnapshot: snapshot.getChildren()) {
                   String spinnerMeds = childSnapshot.child("Medication Name").getValue(String.class);
                   medNames.add(spinnerMeds);
                }
                ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(SetAlarms.this, android.R.layout.simple_spinner_item,medNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                a_MedsNameSpinner.setAdapter(arrayAdapter);
                a_MedsNameSpinner.setOnItemSelectedListener(SetAlarms.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        // Buttons
        a_SelectTimeBtn = findViewById(R.id.selectTimeBtn);
        a_SetAlarmBtn = findViewById(R.id.setAlarmBtn);

        a_SetAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent MyAlarms = new Intent(SetAlarms.this,MyAlarms.class);
                startActivity(MyAlarms);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedmeasure=parent.getItemAtPosition(position).toString();
        Toast.makeText(this,selectedmeasure,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}