package com.example.phmst72021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyMedications extends AppCompatActivity implements MedsAdapter.OnMedListener {

    TextView a_MyMedsTitle;
    ImageButton a_AddBtn;
    ImageButton BackImgBtnArrow;

    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    DatabaseReference firebaseRef;
    MedsAdapter medsAdapter;
    ArrayList<MedicationInfo> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medications);

        // TextViews
        a_MyMedsTitle = findViewById(R.id.MyMedsTitle);

        // Recycler View
        recyclerView = findViewById(R.id.medsList);

        // Get the UserID of current user
        String currentUser = fAuth.getInstance().getCurrentUser().getUid();

        firebaseRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser).child("Medications");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        medsAdapter = new MedsAdapter(this,list, this);
        recyclerView.setAdapter(medsAdapter);

        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MedicationInfo meds = dataSnapshot.getValue(MedicationInfo.class);
                    list.add(meds);
                }
                medsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Buttons
        a_AddBtn = findViewById(R.id.AddImgBtn);
        a_AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddMeds = new Intent(MyMedications.this,AddMeds.class);
                startActivity(AddMeds);
                //finish();
            }
        });

        BackImgBtnArrow = findViewById(R.id.BackImgBtn);
        BackImgBtnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Med = new Intent(MyMedications.this,Medication.class);
                startActivity(Med);
                finish();
            }
        });
    }

    @Override
    public void onMedClick(int position) {
        list.get(position);
        Intent intent = new Intent(this,ViewMed.class);
        intent.putExtra("MedName",list.get(position).getMedName());
        intent.putExtra("DocName",list.get(position).getDocName());
        intent.putExtra("GivenDate",list.get(position).getGivenDate());
        intent.putExtra("ExpDate",list.get(position).getExpDate());
        intent.putExtra("AmountGiven",list.get(position).getAmountGiven());
        intent.putExtra("DosageAmount",list.get(position).getDosageAmount());
        intent.putExtra("Notes",list.get(position).getNotes());
        startActivity(intent);

    }
}