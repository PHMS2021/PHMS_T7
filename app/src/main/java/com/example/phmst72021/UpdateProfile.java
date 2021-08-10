package com.example.phmst72021;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;



public class UpdateProfile extends AppCompatActivity {
    FirebaseDatabase ProfileNode;
    DatabaseReference ref;
    FirebaseAuth mAuth;
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

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    String UserName = "", Gender = "", PatientNameFirst = "",PatientNameMiddle = "",
            PatientNameLast = "", Age = "", Weight = "", Height = "", DoctorName = "",
            VisitDate = "", AnnualCheckUpDate = "", UserType = "", DoctorNote = "";

    Button addBtn, removeBtn, submit;
    int year, day, month;
    private View v;
    Intent intent;
    Context context;
    LinearLayout doctors;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        context = this;
        intent = getIntent();
        userType = (RadioGroup) findViewById(R.id.radiogroup);
        userName = (EditText) findViewById(R.id.Username);
        email = (EditText) findViewById(R.id.email1);
        patientNameFirst = (EditText) findViewById(R.id.patient_name_first);
        patientNameMiddle = (EditText) findViewById(R.id.patient_name_middle);
        patientNameLast = (EditText) findViewById(R.id.patient_name_last);
        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        doctorName = (EditText) findViewById(R.id.doctor_name);
        visitDate = (EditText) findViewById(R.id.visit_date);
        gender = (Spinner) findViewById(R.id.gender);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        annualCheckUpDate = findViewById(R.id.annualCheckUpDate)
        annualCheckUpDate.setInputType(InputType.TYPE_NULL);
        annualCheckUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                //date picker dialog
                picker = new DatePickerDialog(AddMeds.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateGiven.setText((month+1)+"/"+dayOfMonth+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });
        submit = findViewById(R.id.submit);

        ProfileNode = FirebaseDatabase.getInstance();
        String currentUser = mAuth.getInstance().getCurrentUser().getUid();
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



