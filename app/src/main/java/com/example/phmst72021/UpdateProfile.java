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
import com.google.firebase.database.DataSnapshot;
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

    Button addBtn, removeBtn, btn_submit;
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
        patientNameFirst = findViewById(R.id.patient_name_first);
        patientNameMiddle = findViewById(R.id.patient_name_middle);
        patientNameLast = findViewById(R.id.patient_name_last);
        age = findViewById(R.id.age);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        doctorName = findViewById(R.id.doctor_name);
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
            picker[0] = new DatePickerDialog(UpdateProfile.this, (view, year1, month1, dayOfMonth) -> annualCheckUpDate.setText((month1 + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker[0].show();
        });

        visitDate = findViewById(R.id.visit_date);
        visitDate.setInputType(InputType.TYPE_NULL);
        visitDate.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

            //date picker dialog
            picker[0] = new DatePickerDialog(UpdateProfile.this, (view, year1, month1, dayOfMonth) -> visitDate.setText((month1 + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker[0].show();
        });

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
                Intent MyMeds = new Intent(UpdateProfile.this, Home.class);
                startActivity(MyMeds);
                finish();
            }
        });
    }


    public void updateProfile(){
        database = FirebaseDatabase.getInstance();
        String currentUser = mAuth.getInstance().getCurrentUser().getUid();
        String UserName = userName.getText().toString().trim(),
        PatientNameFirst = patientNameFirst.getText().toString().trim(),
        PatientNameMiddle = patientNameMiddle.getText().toString().trim(),
        PatientNameLast = patientNameLast.getText().toString().trim(),
        Age = age.getText().toString().trim(),
        Weight = weight.getText().toString().trim(),
        Height = height.getText().toString().trim(),
        DoctorName = doctorName.getText().toString().trim(),
        VisitDate = visitDate.getText().toString().trim(),
        Gender = gender.getText().toString().trim(),
        AnnualCheckUpDate = annualCheckUpDate.getText().toString().trim(),
        DoctorNote = doctorNote.getText().toString().trim();
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        userType = (RadioGroup) findViewById(R.id.radiogroup);
        String UserType = ((RadioButton) findViewById(userType.getCheckedRadioButtonId())).getText().toString();
        Log.d("Update Profile for: ", currentUser);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("age", Age);
        hashMap2.put("annualCheckUpDate", AnnualCheckUpDate);
        hashMap2.put("doctorName", DoctorName);
        hashMap2.put("doctorNote", DoctorNote);
        hashMap2.put("gender", Gender);
        hashMap2.put("height", Height);
        hashMap2.put("patientNameFirst", PatientNameFirst);
        hashMap2.put("patientNameLast", PatientNameLast);
        hashMap2.put("patientNameMiddle", PatientNameMiddle);
        hashMap2.put("userName", UserName);
        hashMap2.put("userType", UserType.trim());
        hashMap2.put("visitDate", VisitDate);
        hashMap2.put("weight", Weight);


        ref = database.getReference("Users").child(currentUser).child("Profile");
        Log.d("Update Profile at", String.valueOf(ref));
        Log.d("Update Profile with", String.valueOf(hashMap2));

        ref.updateChildren(hashMap2);
        ref.child("Profile").updateChildren(hashMap2).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d("Update Profile successful for: ", currentUser);
                Toast.makeText(UpdateProfile.this, "Profile Updated.", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Update Profile failed for: ", currentUser + " because: " + task.getException());
                Toast.makeText(UpdateProfile.this, "Update failed.", Toast.LENGTH_SHORT).show();
            }

        });
    }

}



