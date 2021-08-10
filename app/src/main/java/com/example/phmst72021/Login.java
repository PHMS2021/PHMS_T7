package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    private static final String TAG = "Account";
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    EditText email, password;
    String email1, password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.patient_name_first);
    }
    public void close(){
        this.finish();
    }
    public void onSubmit(View view){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email1 = email.getText().toString();
                Log.e("Email", email1);
                password1 = password.getText().toString();
                if(email1.matches("") || password1.matches(""))
                {
                    Log.e("Login", "Password or Email blank");
                    Toast.makeText(Login.this, "Password and Email cannot be blank.",Toast.LENGTH_SHORT).show();
                }
                else signIn(email1, password1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onSignUpClick(View view){
        Intent intent = new Intent(this, Create_Account.class);
        startActivity(intent);
        close();
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        Toast.makeText(this, "Log in successful!", Toast.LENGTH_SHORT).show();
                        String currentUser = mAuth.getInstance().getCurrentUser().getUid();
                        Log.e("Sign In User", currentUser);
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(this, "Log in failed. Password or email incorrect.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}