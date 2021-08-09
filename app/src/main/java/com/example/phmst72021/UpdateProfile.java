package com.example.phmst72021;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UpdateProfile extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    Intent intent;
    Context context;
    LinearLayout doctors;
    private Calendar calendar;
    User user;
    String gen;
    EditText userName, password,patientName, age, weight, height, doctorName, visitDate, annualCheckUpDate;
    Spinner gender;
    RadioGroup type;
    RadioButton radioButton;
    String[] genders = new String[]{
            "Gender",
            "Male",
            "Female",
            "Other"
    };
    Button addBtn, removeBtn, submit;
    int year, day, month;
    List<String> genderList = new ArrayList<>(Arrays.asList(genders));
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        context = this;
        intent = getIntent();
        user = intent.getParcelableExtra("User");
        userName = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        patientName = (EditText) findViewById(R.id.patient_name);
        type = (RadioGroup) findViewById(R.id.radiogroup);
        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        gender = (Spinner) findViewById(R.id.gender);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        doctors = findViewById(R.id.doctors);
        addBtn = findViewById(R.id.addDoctor);
        submit = findViewById(R.id.submit);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,R.layout.spinner_text,genderList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_text);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position > 0){
                    gen = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userName.setText(user.userName);
        password.setText(user.password);
        patientName.setText(user.patientName);
        type.check(user.role.equals("Patient") ? R.id.patient : R.id.careTaker);
        age.setText(user.age);
        weight.setText(user.weight);
        height.setText(user.height);
        gender.setSelection(adapter.getPosition(user.gender));
        for (Detail detail : user.details) {
            View doctorView = getLayoutInflater().inflate(R.layout.row_add_doctor, null, false);
            doctors.addView(doctorView);
            removeBtn = (Button) doctorView.findViewById(R.id.remove_doctor);
            EditText doctor = (EditText) doctorView.findViewById(R.id.doctor_name);
            EditText visit = (EditText)  doctorView.findViewById(R.id.visit_date);
            EditText annual = (EditText)  doctorView.findViewById(R.id.checkup_date);
            doctor.setText(detail.doctorName);
            visit.setText(detail.visitDate);
            annual.setText(detail.checkUpDate);
            visit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(v.getContext(), new
                            DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub
                                    // arg1 = year
                                    // arg2 = month
                                    // arg3 = day
                                    visit.setText(new StringBuilder().append(arg3).append("/")
                                            .append(arg2).append("/").append(arg1));
                                }
                            }, year, month, day);
                    datePickerDialog.setTitle("Select Visit Date");
                    datePickerDialog.show();
                }
            });
            annual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(v.getContext(), new
                            DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub
                                    // arg1 = year
                                    // arg2 = month
                                    // arg3 = day
                                    annual.setText(new StringBuilder().append(arg3).append("/")
                                            .append(arg2).append("/").append(arg1));
                                }
                            }, year, month, day);
                    datePickerDialog.setTitle("Select Visit Date");
                    datePickerDialog.show();
                }
            });
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doctors.removeView(doctorView);
                }
            });
        }
    }

    public void onUpdateClick() {
        onUpdateClick();
    }

    public void onUpdateClick(View v){
        this.v = v;
        int selectedId = type.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        ArrayList<Detail> details = new ArrayList<>();
        for(int i = 0; i<doctors.getChildCount(); i++){
            View view = doctors.getChildAt(i);
            doctorName = (EditText) view.findViewById(R.id.doctor_name);
            visitDate = (EditText) view.findViewById(R.id.visit_date);
            annualCheckUpDate = (EditText) view.findViewById(R.id.checkup_date);
            details.add(new Detail(doctorName.getText().toString(), visitDate.getText().toString(), annualCheckUpDate.getText().toString()));
        }
        ref.child(userName.getText().toString()).setValue(new User(userName.getText().toString(), password.getText().toString(),
                patientName.getText().toString(), radioButton.getText().toString(), gen, age.getText().toString(), weight.getText().toString(),
                height.getText().toString(), details));

        Intent newIntent = new Intent(this, com.example.phmst72021.Home.class);
        newIntent.putExtra("User", user);
        startActivity(newIntent);
        close();
    }
    public void addDoctorView(View view) {
        View doctorView = getLayoutInflater().inflate(R.layout.row_add_doctor, null, false);
        doctors.addView(doctorView);
        removeBtn = (Button) doctorView.findViewById(R.id.remove_doctor);
        EditText visit = (EditText)  doctorView.findViewById(R.id.visit_date);
        EditText annual = (EditText)  doctorView.findViewById(R.id.checkup_date);
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(v.getContext(), new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0,
                                                  int arg1, int arg2, int arg3) {
                                // TODO Auto-generated method stub
                                // arg1 = year
                                // arg2 = month
                                // arg3 = day
                                visit.setText(new StringBuilder().append(arg3).append("/")
                                        .append(arg2).append("/").append(arg1));
                            }
                        }, year, month, day);
                datePickerDialog.setTitle("Select Visit Date");
                datePickerDialog.show();
            }
        });
        annual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(v.getContext(), new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0,
                                                  int arg1, int arg2, int arg3) {
                                // TODO Auto-generated method stub
                                // arg1 = year
                                // arg2 = month
                                // arg3 = day
                                annual.setText(new StringBuilder().append(arg3).append("/")
                                        .append(arg2).append("/").append(arg1));
                            }
                        }, year, month, day);
                datePickerDialog.setTitle("Select Visit Date");
                datePickerDialog.show();
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctors.removeView(doctorView);
            }
        });
    }

    public void close(){
        this.finish();
    }

}