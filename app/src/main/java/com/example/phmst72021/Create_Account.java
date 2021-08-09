package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Create_Account extends AppCompatActivity {
    private static final String TAG = "Account";
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    EditText email, password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        email = (EditText) findViewById(R.id.editTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success - " + email + " " + password);
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        Intent intent = new Intent(Create_Account.this, Login.class);
                        startActivity(intent);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure - " + email + " " + password, task.getException());
                        Toast.makeText(Create_Account.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    public void onLoginClick(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        close();
    }

    public void close(){
        this.finish();
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }

    public void onSubmit(View view){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email1 = email.getText().toString();
                Log.e("Email", email1);
                String password1 = password.getText().toString();
                Log.e("Password", password1);
                if(email1.matches("") || password1.matches(""))
                {
                    Log.e("Login", "Password or Email blank");
                    Toast.makeText(Create_Account.this, "Password and Email cannot be blank.",Toast.LENGTH_SHORT).show();
                }
                else {
                    createAccount(email1, password1);
                    sendEmailVerification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

