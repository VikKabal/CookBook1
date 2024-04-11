package com.example.cookbook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> foodNames;
    ArrayList<String> filteredFoodNames;
    DBHelper DB;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        FloatingActionButton updateBtn = findViewById(R.id.updatebtn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Otevření aktivity pro přidání nového receptu
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aktualizace databáze
                boolean success = updateDatabase();
                if (success) {
                    Toast.makeText(MainActivity.this, "Databáze byla úspěšně aktualizována", Toast.LENGTH_SHORT).show();
                    // Obnovení seznamu receptů
                    refreshRecipeList();
                } else {
                    Toast.makeText(MainActivity.this, "Nepodařilo se aktualizovat databázi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView = findViewById(R.id.listView);
        foodNames = new ArrayList<>();
        filteredFoodNames = new ArrayList<>();
        DB = new DBHelper(this);

        // Načtení seznamu receptů
        loadRecipeNames();

        customAdapter = new CustomAdapter(MainActivity.this, foodNames);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFoodName = filteredFoodNames.get(i);
                // Otevření aktivity s detaily receptu
                Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("foodName", selectedFoodName);
                startActivity(intent);
            }
        });

        // Nastavení listeneru pro SearchView
        SearchView searchView = findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrace seznamu podle zadaného textu ve SearchView
                filter(newText);
                return true;
            }
        });
    }

    // Metoda pro načtení seznamu receptů z databáze
    private void loadRecipeNames() {
        Cursor cursor = DB.getdata();
        if (cursor.getCount() == 0) {
            // Žádné záznamy
            return;
        }
        while (cursor.moveToNext()) {
            String foodName = cursor.getString(0);
            foodNames.add(foodName);
            filteredFoodNames.add(foodName); // Přidání do filtrovaného seznamu
        }
    }

    // Metoda pro aktualizaci databáze
    private boolean updateDatabase() {
        // Implementace aktualizace databáze podle potřeb
        return true; // True pokud aktualizace proběhla úspěšně, jinak false
    }

    // Metoda pro obnovení seznamu receptů
    private void refreshRecipeList() {
        foodNames.clear();
        filteredFoodNames.clear();
        loadRecipeNames();
        customAdapter.notifyDataSetChanged();
    }

    // Metoda pro filtrování seznamu podle zadaného textu
    private void filter(String text) {
        filteredFoodNames.clear();
        if (TextUtils.isEmpty(text)) {
            // Pokud je text prázdný, zobrazit všechny položky
            filteredFoodNames.addAll(foodNames);
        } else {
            // Jinak filtrovat podle zadaného textu
            for (String name : foodNames) {
                if (name.toLowerCase().contains(text.toLowerCase())) {
                    filteredFoodNames.add(name);
                }
            }
        }
        customAdapter.notifyDataSetChanged();
    }
}
