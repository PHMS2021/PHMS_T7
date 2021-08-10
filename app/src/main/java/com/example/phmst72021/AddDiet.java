package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDiet extends AppCompatActivity {

    EditText calorie, water, Food, recipes, description, notes, articles;
    Button submit;
    User user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Diet");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        articles = findViewById(R.id.articles);
        user = getIntent().getParcelableExtra("User");
        calorie = findViewById(R.id.calorie);
        water = findViewById(R.id.water);
        Food = findViewById(R.id.daily_food);
        recipes = findViewById(R.id.recipes);
        description = findViewById(R.id.description);
        notes = findViewById(R.id.general_notes);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Diet Saved Successfully", Toast.LENGTH_SHORT).show();
            Intent newIntent = new Intent(v.getContext(), Home.class);
            newIntent.putExtra("User", user);
            startActivity(newIntent);
            close();
        });
    }
    public void close(){
        this.finish();
    }

}

class Diet{
    String userName;
    String calorie;
    String water;
    String dailyFood;
    String recipes;
    String description;
    String articles;
    String notes;

    public Diet(String userName, String calorie, String water, String dailyFood, String recipes, String description, String articles, String notes) {
        this.userName = userName;
        this.calorie = calorie;
        this.water = water;
        this.dailyFood = dailyFood;
        this.recipes = recipes;
        this.description = description;
        this.articles = articles;
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getDailyFood() {
        return dailyFood;
    }

    public void setDailyFood(String dailyFood) {
        this.dailyFood = dailyFood;
    }

    public String getRecipes() {
        return recipes;
    }

    public void setRecipes(String recipes) {
        this.recipes = recipes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}