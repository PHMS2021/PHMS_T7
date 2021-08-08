package com.example.phmst72021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDiet extends AppCompatActivity {

    EditText calory, water, dailyFood, recipes, description, articles, notes;
    Button submit;
    User user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Diet");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        user = getIntent().getParcelableExtra("User");
        calory = (EditText) findViewById(R.id.calory);
        water = (EditText) findViewById(R.id.water);
        dailyFood = (EditText) findViewById(R.id.daily_food);
        recipes = (EditText) findViewById(R.id.recipes);
        description = (EditText) findViewById(R.id.description);
        articles = (EditText) findViewById(R.id.articles);
        notes = (EditText) findViewById(R.id.general_notes);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diet diet = new Diet(user.userName, calory.getText().toString(), water.getText().toString(),
                        dailyFood.getText().toString(), recipes.getText().toString(),
                        description.getText().toString(), articles.getText().toString(),
                        notes.getText().toString());
                ref.child(diet.userName).setValue(diet);
                Toast.makeText(v.getContext(), "Diet Saved Successfully", Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(v.getContext(), Home.class);
                newIntent.putExtra("User", user);
                startActivity(newIntent);
                close();
            }
        });
    }
    public void close(){
        this.finish();
    }

}

class Diet{
    String userName;
    String calory;
    String water;
    String dailyFood;
    String recipes;
    String description;
    String articles;
    String notes;

    public Diet(String userName, String calorie, String water, String dailyFood, String recipes, String description, String articles, String notes) {
        this.userName = userName;
        this.calory = calorie;
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

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
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