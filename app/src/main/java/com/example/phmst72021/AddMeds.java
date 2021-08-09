package com.example.phmst72021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class AddMeds extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView a_AddMedsTitle, a_MedNameTitle, a_DocNameTitle, a_AmountGivenTitle, a_DosageAmountTitle, a_NotesTitle, a_DateGivenTitle, a_ExpiryDateTitle;
    EditText MedName, DocName, AmountGiven, DosageAmount, Notes, DateGiven, ExpiryDate;
    Button SetAddMedBtn;
    DatePickerDialog picker;

    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);

        Spinner Amount = findViewById(R.id.Amount_spinner);
        Spinner Dosage = findViewById(R.id.Dosage_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.MedMeasurements, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Amount.setAdapter(adapter);
        Amount.setOnItemSelectedListener(this);

        Dosage.setAdapter(adapter);
        Dosage.setOnItemSelectedListener(this);

        fAuth = FirebaseAuth.getInstance();
        //Database



        // All Textviews
        a_AddMedsTitle = findViewById(R.id.AddMedsTitle);
        a_MedNameTitle = findViewById(R.id.MedNameTitle);
        a_DocNameTitle = findViewById(R.id.DocNameTitle);
        a_AmountGivenTitle = findViewById(R.id.AmountGivenTitle);
        a_DosageAmountTitle = findViewById(R.id.DosageTitle);
        a_NotesTitle = findViewById(R.id.NotesTitle);
        a_DateGivenTitle = findViewById(R.id.DateGivenTitle);
        a_ExpiryDateTitle = findViewById(R.id.ExpiryDateTitle);

        MedName = findViewById(R.id.MedNameInput);
        DocName = findViewById(R.id.DocNameInput);
        AmountGiven = findViewById(R.id.AmountGivenInput);
        DosageAmount = findViewById(R.id.DosageInput);
        Notes = findViewById(R.id.NotesInput);

        DateGiven = findViewById(R.id.DateGivenInput);
        DateGiven.setInputType(InputType.TYPE_NULL);
        DateGiven.setOnClickListener(new View.OnClickListener() {
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

        ExpiryDate = findViewById(R.id.ExpiryDateInput);
        ExpiryDate.setInputType(InputType.TYPE_NULL);
        ExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar expCldr = Calendar.getInstance();
                int day = expCldr.get(Calendar.DAY_OF_MONTH);
                int month = expCldr.get(Calendar.MONTH);
                int year = expCldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(AddMeds.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ExpiryDate.setText((month+1)+"/"+dayOfMonth+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        // Buttons
        SetAddMedBtn = findViewById(R.id.AddMedBtn);
        SetAddMedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Spinners
                String AmountMeasure = Amount.getSelectedItem().toString();
                String DosageMeasure = Dosage.getSelectedItem().toString();

                // EditTexts
                String a_MedName = MedName.getText().toString().trim();
                String a_DocName = DocName.getText().toString().trim();
                String a_Notes = Notes.getText().toString().trim();

                // DatePickers
                String a_GivenDate = DateGiven.getText().toString();
                String a_ExpDate = ExpiryDate.getText().toString();

                int value = 0;

                String a_AmountGiven = AmountGiven.getText().toString();
                /*if(!"".equals(a_AmountGiven)){
                    value=Integer.parseInt(a_AmountGiven);
                }*/

                String a_DosageAmount = DosageAmount.getText().toString();
                /*if(!"".equals(a_DosageAmount)){
                    value=Integer.parseInt(a_DosageAmount);
                }*/

                Intent MyMeds = new Intent(AddMeds.this,MyMedications.class);
                startActivity(MyMeds);
                finish();

                rootNode = FirebaseDatabase.getInstance();
                String currentUser = fAuth.getInstance().getCurrentUser().getUid();
                firebaseRef = rootNode.getReference("Users").child(currentUser).child("Medications").child(a_MedName);

                MedicationInfo medInfo = new MedicationInfo(a_MedName,a_DocName,a_AmountGiven+" "+AmountMeasure,a_GivenDate,a_ExpDate,a_DosageAmount+" "+DosageMeasure,a_Notes);
                firebaseRef.setValue(medInfo);
                //get all the values


                // Update Database
                /*HashMap hashMap = new HashMap();
                hashMap.put("Med_Name", a_MedName);
                hashMap.put("Doc_Name", a_DocName);
                hashMap.put("Amount_Med_Given", a_AmountGiven+" "+AmountMeasure);
                hashMap.put("Date_Med_Given", a_GivenDate);
                hashMap.put("Date_Med_Exp", a_ExpDate);
                hashMap.put("Dosage_Amount", a_DosageAmount+" "+DosageMeasure);
                hashMap.put("General_Notes", a_Notes);

                firebaseRef.child("Test").child("Medications").child(a_MedName).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(AddMeds.this,"Database Updated", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String selectedmeasure=parent.getItemAtPosition(position).toString();
        //Toast.makeText(this,selectedmeasure,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}