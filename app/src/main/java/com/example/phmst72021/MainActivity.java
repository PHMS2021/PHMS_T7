/*package com.example.phmst72021;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout doctors;
    EditText userName, password,patientName, age, weight, height, doctorName, visitDate, annualCheckUpDate;
    Spinner gender;
    private int year, month, day;
    Button addBtn, removeBtn, submit;
    String gen;
    RadioGroup type;
    RadioButton radioButton;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    String[] genders = new String[]{
            "Gender",
            "Male",
            "Female",
            "Other"
    };
    List<String> genderList = new ArrayList<>(Arrays.asList(genders));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.patient_name_first);
        patientName = (EditText) findViewById(R.id.patient_name_middle);
        type = (RadioGroup) findViewById(R.id.radiogroup);
        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        gender = (Spinner) findViewById(R.id.gender);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,R.layout.spinner_text,genderList){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
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



        doctors = findViewById(R.id.doctors);
        addBtn = findViewById(R.id.addDoctor);
        submit = findViewById(R.id.submit);
        addBtn.setOnClickListener(this);
        submit.setOnClickListener(v -> {
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
            startActivity(new Intent(v.getContext(), Login.class));
            close();
        });
    }
    public void onLoginClick(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        close();
    }

    public void close(){
        this.finish();
    }



class Detail implements Parcelable {
    String doctorName;
    String visitDate;
    String checkUpDate;
    public Detail(String doctorName, String visitDate, String checkUpDate) {
        this.doctorName = doctorName;
        this.visitDate = visitDate;
        this.checkUpDate = checkUpDate;
    }

    protected Detail(Parcel in) {
        doctorName = in.readString();
        visitDate = in.readString();
        checkUpDate = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getCheckUpDate() {
        return checkUpDate;
    }

    public void setCheckUpDate(String checkUpDate) {
        this.checkUpDate = checkUpDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(doctorName);
        dest.writeString(visitDate);
        dest.writeString(checkUpDate);
    }
}

class User implements Parcelable{
    String userName;
    String password;
    String patientName;
    String gender;
    String age;
    String weight;
    String height;
    String role;
    ArrayList<Detail> details;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Detail> details) {
        this.details = details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(patientName);
        dest.writeString(gender);
        dest.writeString(age);
        dest.writeString(weight);
        dest.writeString(height);
        dest.writeString(role);
        dest.writeTypedList(details);
    }
    }
}

 */