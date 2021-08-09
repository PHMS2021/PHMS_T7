package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    Button update, delete, diet, logout;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    Intent intent;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intent = getIntent();
        user = (User) intent.getParcelableExtra("User");
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        diet = (Button) findViewById(R.id.diet);
        logout = (Button) findViewById(R.id.LogOut);
    }
    public void onDeleteClick(View view){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("userName").getValue(String.class);
                    String role = child.child("role").getValue(String.class);
                    if(name.equals(user.userName) &&
                            role.equals(user.role)){
                        child.getRef().removeValue();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                        close();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onUpdateClick(View view){
        Intent newIntent = new Intent(view.getContext(), UpdateProfile.class);
        newIntent.putExtra("User", user);
        startActivity(newIntent);
        close();
    }
    public void onDietClick(View view){
        Intent newIntent = new Intent(view.getContext(), AddDiet.class);
        newIntent.putExtra("User", user);
        startActivity(newIntent);
        close();
    }
    public void close(){
        this.finish();
    }

    public void onLogOutClick(View view) {
        Intent newIntent = new Intent(view.getContext(), Login.class);
        newIntent.putExtra("User", user);
        startActivity(newIntent);
        close();
    }
}