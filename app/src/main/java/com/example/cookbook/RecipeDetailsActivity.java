package com.example.cookbook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class RecipeDetailsActivity extends AppCompatActivity {

    TextView foodNameTextView, ingredientsTextView, instructionsTextView;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        foodNameTextView = findViewById(R.id.foodNameTextView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        instructionsTextView = findViewById(R.id.instructionsTextView);

        DB = new DBHelper(this);

        // Získání názvu jídla z intentu
        String foodName = getIntent().getStringExtra("foodName");

        displayRecipeDetails(foodName);
    }

    private void displayRecipeDetails(String foodName) {
        Cursor cursor = DB.getdata();
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(foodName)) {
                String ingredients = cursor.getString(1);
                String instructions = cursor.getString(2);

                foodNameTextView.setText(foodName);
                ingredientsTextView.setText(ingredients);
                instructionsTextView.setText(instructions);

                return;
            }
        }

        // Pokud se recept s daným názvem nenalezl
        Toast.makeText(this, "Recept nebyl nalezen", Toast.LENGTH_SHORT).show();
        finish(); // Ukončit aktivitu
    }
}