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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Create_Account extends AppCompatActivity {
    private static final String TAG = "Account";
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    EditText email, password;
    String userName = "", gender = "", patientNameFirst = "", patientNameMiddle = "",
            patientNameLast = "", age = "", weight = "", height = "", doctorName = "",
            visitDate = "", annualCheckUpDate = "", userType = "", doctorNote = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);

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

    public void checkEmailCreate(String email, String password, FirebaseAuth ref1) {
        ref1.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                    if (isNewUser) {
                        createAccount(email, password);
                    } else {
                        {
                            Log.e("Check Email Create", "Email exists");
                            Toast.makeText(Create_Account.this, "Email in use! Click 'Forgot Password' to receive reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void checkEmailReset(String email, FirebaseAuth ref1) {
        ref1.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                    if (isNewUser) {
                        Log.e("Check Email Reset", "Email does not exist");
                        Toast.makeText(Create_Account.this, "Email not in use! Click 'Create Account' to create account", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        {
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                        Toast.makeText(Create_Account.this, "Reset Password email sent to " + email, Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(Create_Account.this, "Reset Password failed: " + email, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
    }


    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success - " + email);
                        FirebaseUser user = mAuth.getCurrentUser();
                        String currentUser = mAuth.getInstance().getCurrentUser().getUid();
                        updateUI(user);
                        database = FirebaseDatabase.getInstance();
                        ref = database.getReference("Users").child(currentUser);
                        Log.d("Create Account for: ", currentUser);
                        Log.d("Create Account at: ", String.valueOf(ref));
                        ref.setValue(currentUser);
                        createProfile();
                        sendEmailVerification();
                        Intent intent = new Intent(Create_Account.this, Login.class);
                        startActivity(intent);
                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure - " + email + " " + password, task.getException());
                        Toast.makeText(Create_Account.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void createProfile(){
        database = FirebaseDatabase.getInstance();
        String currentUser = mAuth.getInstance().getCurrentUser().getUid();
        Log.d("Create Profile for: ", currentUser);
        HashMap hashMap = new HashMap();
        hashMap.put("userName", userName);
        hashMap.put("gender", gender);
        hashMap.put("patientNameFirst", patientNameFirst);
        hashMap.put("patientNameMiddle", patientNameMiddle);
        hashMap.put("patientNameLast", patientNameLast);
        hashMap.put("age", age);
        hashMap.put("weight", weight);
        hashMap.put("height", height);
        hashMap.put("doctorName", doctorName);
        hashMap.put("visitDate", visitDate);
        hashMap.put("annualCheckUpDate", annualCheckUpDate);
        hashMap.put("userType", userType);
        hashMap.put("doctorNote", doctorNote);
        ref = database.getReference("Users").child(currentUser).child("Profile");
        Log.d("Create Profile at", String.valueOf(ref));
        Log.d("Create Profile as", String.valueOf(hashMap));
        ref.updateChildren(hashMap).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d("Create Profile successful for: ", currentUser);
            } else {
                Log.e("Create Profile failed for: ", currentUser + " because: " + task.getException());
                Toast.makeText(Create_Account.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        Log.e("Create", String.valueOf(user.getEmail()));
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
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

    private void reload() { }

    private void updateUI(FirebaseUser user) {
    }

    public void onSubmit1(View view){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email1 = email.getText().toString();
                Log.e("Email", email1);
                String password1 = password.getText().toString();
                Log.e("Password", password1);
                if(email1.matches("") || password1.matches(""))
                {
                    Log.e("Create", "Password or Email blank");
                    Toast.makeText(Create_Account.this, "Password and Email cannot be blank.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("Create", email1 + " " + password1);
                    checkEmailCreate(email1, password1, mAuth);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void logInClick(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        close();
    }

    public void ForgotPassword(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email1 = email.getText().toString();
        Log.e("PW Reset",email1);
        if(!email1.equals("") && !email1.equals(null)) {
            checkEmailReset(email1, auth);
        }
        else Toast.makeText(Create_Account.this, "Email field cannot be blank", Toast.LENGTH_SHORT).show();
    }
}



