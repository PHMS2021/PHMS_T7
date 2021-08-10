package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Home extends AppCompatActivity {

    Button update, delete, diet, meds,logout;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;

    DatabaseReference ref = database.getReference("Users");
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        diet = findViewById(R.id.diet);
        meds =  findViewById(R.id.meds);
        logout =  findViewById(R.id.LogOut);
    }
    public void onDeleteClick(View view){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");
                String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                assert user != null;
                user.reauthenticate(credential).addOnCompleteListener(task -> user.delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Log.e("Delete", currentUser);
                        ref = database.getReference("Users").child(currentUser);
                        Log.e("Delete", String.valueOf(ref));
                        ref.setValue(null);
                        Log.d("DELETE", "User account deleted.");
                        Toast.makeText(Home.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }));
                Intent newIntent = new Intent(view.getContext(), Login.class);
                startActivity(newIntent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onUpdateClick(View view){
        Intent newIntent = new Intent(view.getContext(), UpdateProfile.class);
        startActivity(newIntent);
        //close();
    }
    public void onDietClick(View view){
        Intent newIntent = new Intent(view.getContext(), AddDiet.class);
        startActivity(newIntent);
       // close();
    }
    public void close(){
        this.finish();
    }

    public void onMedsClick(View view){
        Intent newIntent = new Intent(view.getContext(), Medication.class);
        startActivity(newIntent);
       // close();
    }


    public void onLogOutClick(View view) {
        Intent newIntent = new Intent(view.getContext(), Login.class);
        startActivity(newIntent);
        AuthUI.getInstance().signOut(this);
        close();
    }
}