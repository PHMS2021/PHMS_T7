package com.example.phmst72021;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;


public class UpdateProfile extends AppCompatActivity {
    FirebaseDatabase ProfileNode;

    EditText email;
    EditText userName;
    Spinner gender;
    EditText patientNameFirst;
    EditText patientNameMiddle;
    EditText patientNameLast;
    EditText age;
    EditText weight;
    EditText height;
    EditText doctorName;
    EditText visitDate;
    EditText annualCheckUpDate;
    RadioGroup userType;
    RadioButton radioButton;
    String gen;
    String[] genders = new String[]{
            "Gender",
            "Male",
            "Female",
            "Other"
    };

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String UserName = "", Gender = "", PatientNameFirst = "",PatientNameMiddle = "",
            PatientNameLast = "", Age = "", Weight = "", Height = "", DoctorName = "",
            VisitDate = "", AnnualCheckUpDate = "", UserType = "", DoctorNote = "";

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
}
 /*       userType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup userType, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                Integer id = userType.getId();
                String userTypeText = id.toString();
                Toast.makeText(getApplicationContext(), userTypeText, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

  */



