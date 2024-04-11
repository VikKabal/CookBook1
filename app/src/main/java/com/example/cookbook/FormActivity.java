package com.example.cookbook;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {
    EditText foodname, ingredients, instructions;
    Button insertphoto, insert;
    DBHelper DB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        foodname = findViewById(R.id.foodname);
        ingredients = findViewById(R.id.ingredients);
        instructions = findViewById(R.id.instructions);

        insert = findViewById(R.id.buttonAddRecipe);
        //update = findViewById(R.id.updatebtn);
        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodnameTXT = foodname.getText().toString();
                String ingredientsTXT = ingredients.getText().toString();
                String instructionsTXT = instructions.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(foodnameTXT, ingredientsTXT, instructionsTXT);
                if(checkinsertdata==true)
                    Toast.makeText(FormActivity.this, "Recept byl přidan", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(FormActivity.this, "Nepovedlo se přidat recept", Toast.LENGTH_SHORT).show();

            }
        });
       /* update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodnameTXT = foodname.getText().toString();
                String ingredientsTXT = ingredients.getText().toString();
                String instructionsTXT = instructions.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(foodnameTXT, ingredientsTXT, instructionsTXT);
                if(checkupdatedata==true)
                    Toast.makeText(FormActivity.this, "New entry inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(FormActivity.this, "New entry not inserted", Toast.LENGTH_SHORT).show();

            }
        });*/
    }
}
