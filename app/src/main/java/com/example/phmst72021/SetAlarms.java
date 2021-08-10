package com.example.phmst72021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetAlarms extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    private NotifyMedAlarm notifyMedAlarm;

    TextView a_AddAlarmsTitle, a_TimeSet, a_RepeatDaysAlarm, a_MedsTitleSpinner, a_AlarmNotesTitle;
    EditText a_AlarmNotes;
    Button a_SelectTimeBtn, a_CancelAlarmBtn;
    ImageButton a_BackImgBtn;

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
        /*a_RepeatDaysAlarm = findViewById(R.id.RepeatDaysAlarm);*/
        a_MedsTitleSpinner = findViewById(R.id.MedsTitleSpinner);
        a_AlarmNotesTitle = findViewById(R.id.AlarmNotesTitle);

        // EditText
        a_AlarmNotes = findViewById(R.id.AlarmNotes);
        notifyMedAlarm = new NotifyMedAlarm(this);

        // Spinner
        Spinner a_MedsNameSpinner = findViewById(R.id.MedNameSpinner);
        medNames = new ArrayList<>();

        // Populate Spinner depending what medication you added
        String currentUser = fAuth.getInstance().getCurrentUser().getUid();
        firebaseRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);
        firebaseRef.child("Medications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot childSnapshot: snapshot.getChildren()) {
                   String spinnerMeds = childSnapshot.child("medName").getValue(String.class);
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
        a_BackImgBtn = findViewById(R.id.a_BackImgBtn);
        a_BackImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Meds = new Intent(SetAlarms.this,Medication.class);
                startActivity(Meds);
                finish();
            }
        });

        a_SelectTimeBtn = findViewById(R.id.selectTimeBtn);
        a_SelectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFrag();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        a_CancelAlarmBtn = findViewById(R.id.cancelAlarmBtn);
        a_CancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedmeasure = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this,selectedmeasure,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        // Get time from time picker
        Calendar timepick = Calendar.getInstance();
        timepick.set(Calendar.HOUR_OF_DAY, hourOfDay);
        timepick.set(Calendar.MINUTE,minute);
        timepick.set(Calendar.SECOND,0);

        // Make the correct format for time when time is set
        if (hourOfDay > 12) {
            if(minute < 10){
            a_TimeSet.setText("Alarm set at:\n" +hourOfDay + ":0" + minute + " PM");
            }
            else {
                a_TimeSet.setText("Alarm set at:\n "+hourOfDay + ":" + minute + " PM");
            }
        } else {
            if(minute < 10){
                a_TimeSet.setText("Alarm set at:\n "+hourOfDay + ":0" + minute + " AM");
            }
            else {
                a_TimeSet.setText("Alarm set at:\n "+hourOfDay + ":" + minute + " AM");
            }
        }
        startAlarm(timepick);
    }


    private void startAlarm(Calendar timepick) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MedAlarmReciever.class);

        // Checking to see if intent will pass in title & message
        intent.putExtra("MedName","Tylenol");
        intent.putExtra("Message", "Time to take 1 Tylenol pill!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (timepick.before(Calendar.getInstance())) {
            timepick.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timepick.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MedAlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        a_TimeSet.setText("Alarm canceled");
    }
}