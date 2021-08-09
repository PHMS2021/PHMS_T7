package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class Home extends AppCompatActivity {

    Button update, delete, diet, meds,logout;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;

    DatabaseReference ref = database.getReference("Users");
    Intent intent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        diet = (Button) findViewById(R.id.diet);
        meds = (Button) findViewById(R.id.meds);
        logout = (Button) findViewById(R.id.LogOut);
    }
    public void onDeleteClick(View view){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");
                assert user != null;
                user.reauthenticate(credential).addOnCompleteListener(task -> user.delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Log.d("DELETE", "User account deleted.");
                    }
                }));

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
        newIntent.putExtra("User", user);
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