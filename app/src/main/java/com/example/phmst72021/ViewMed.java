package com.example.phmst72021;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewMed extends AppCompatActivity {
    TextView MedTitleView, DocTitleView,DocView,DateGivenTitleView,
            DateGivenView,ExpTitleView,ExpView,AmntTitleView, AmntView,DosageTitleView,
            DosageView,NotesTitleView,NotesView;

    String medName, docName, givenDate, expDate, amount, dosage, notes;

    ImageButton BackImgBtnArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_med);

        // All textviews
        MedTitleView = findViewById(R.id.MedicationTitleView);
        DocTitleView = findViewById(R.id.DocNameViewTitle);
        DocView = findViewById(R.id.DocNameView);
        DateGivenTitleView = findViewById(R.id.DateGivenViewTitle);
        DateGivenView = findViewById(R.id.DateGivenView);
        ExpTitleView = findViewById(R.id.ExpiresDateTitleView);
        ExpView = findViewById(R.id.ExpiresDateView);
        AmntTitleView = findViewById(R.id.AmountGivenTitleView);
        AmntView = findViewById(R.id.AmountGivenView);
        DosageTitleView = findViewById(R.id.DosageTitleView);
        DosageView = findViewById(R.id.DosageView);
        NotesTitleView = findViewById(R.id.NotesTitleView);
        NotesView = findViewById(R.id.NotesView);



        Bundle extras = getIntent().getExtras();
        if(extras != null){
            medName = extras.getString("MedName");
            docName = extras.getString("DocName");
            givenDate = extras.getString("GivenDate");
            expDate = extras.getString("ExpDate");
            amount = extras.getString("AmountGiven");
            dosage = extras.getString("DosageAmount");
            notes = extras.getString("Notes");
        }

        MedTitleView.setText(medName);
        DocView.setText(docName);
        DateGivenView.setText(givenDate);
        ExpView.setText(expDate);
        AmntView.setText(amount);
        DosageView.setText(dosage);
        NotesView.setText(notes);



        //Back Button
        BackImgBtnArrow = findViewById(R.id.BackImgBtn);
        BackImgBtnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MyMeds = new Intent(ViewMed.this,MyMedications.class);
                startActivity(MyMeds);
                finish();
            }
        });

    }
}