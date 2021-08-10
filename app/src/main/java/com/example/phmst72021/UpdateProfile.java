package com.example.phmst72021;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class UpdateProfile extends AppCompatActivity {
    FirebaseDatabase ProfileNode;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    EditText email;
    EditText userName;
    EditText gender;
    EditText patientNameFirst;
    EditText patientNameMiddle;
    EditText patientNameLast;
    EditText age;
    EditText weight;
    EditText height;
    EditText doctorName;
    EditText visitDate;
    EditText annualCheckUpDate;
    EditText doctorNote;
    RadioGroup userType;
    RadioButton radioButton;

    Button addBtn, removeBtn, submit;
    int year, day, month;
    private View v;
    Intent intent;
    Context context;
    LinearLayout doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        final DatePickerDialog[] picker = new DatePickerDialog[1];
        context = this;
        intent = getIntent();
        userType = findViewById(R.id.radiogroup);
        userName = findViewById(R.id.Username);
        email = findViewById(R.id.email1);
        patientNameFirst =  findViewById(R.id.patient_name_first);
        patientNameMiddle =  findViewById(R.id.patient_name_middle);
        patientNameLast =  findViewById(R.id.patient_name_last);
        age =  findViewById(R.id.age);
        weight =  findViewById(R.id.weight);
        height =  findViewById(R.id.height);
        doctorName =  findViewById(R.id.doctor_name);
        visitDate = findViewById(R.id.visit_date);
        gender = findViewById(R.id.gender);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        doctorNote = findViewById(R.id.doctor_note);
        annualCheckUpDate = findViewById(R.id.annualCheckUpDate);
        annualCheckUpDate.setInputType(InputType.TYPE_NULL);
        annualCheckUpDate.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

            //date picker dialog
            picker[0] = new DatePickerDialog(UpdateProfile.this, (view, year1, month1, dayOfMonth) -> annualCheckUpDate.setText((month1 +1)+"/"+dayOfMonth+"/"+ year1),year,month,day);
            picker[0].show();
        });
        ProfileNode = FirebaseDatabase.getInstance();
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    public void updateProfile(){
        database = FirebaseDatabase.getInstance();
        String currentUser = mAuth.getInstance().getCurrentUser().getUid();
        userType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup userType, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                Integer id = userType.getId();
                String userTypeText = id.toString();
                Toast.makeText(getApplicationContext(), userTypeText, Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("Create Profile for: ", currentUser);
        HashMap hashMap = new HashMap();
        hashMap.put("userName", userName);
        hashMap.put("gender", gender);
        hashMap.put("patientNameFirst", patientNameFirst);
        hashMap.put("patientNameMiddle", patientNameMiddle);
        hashMap.put("patientNameLast", patientNameLast);
        hashMap.put("age", age);
        hashMap.put("weight", weight);
        hashMap.put("height", height);
        hashMap.put("doctorName", doctorName);
        hashMap.put("visitDate", visitDate);
        hashMap.put("annualCheckUpDate", annualCheckUpDate);
        hashMap.put("userType", userType);
        hashMap.put("doctorNote", doctorNote);
        ref = database.getReference("Users").child(currentUser).child("Profile");
        Log.d("Update Profile at", String.valueOf(ref));
        Log.d("Update Profile with", String.valueOf(hashMap));
        ref.updateChildren(hashMap).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d("Create Profile successful for: ", currentUser);
            } else {
                Log.e("Create Profile failed for: ", currentUser + " because: " + task.getException());
                Toast.makeText(UpdateProfile.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }

        });
    }

}



