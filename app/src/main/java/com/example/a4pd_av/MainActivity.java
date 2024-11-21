package com.example.a4pd_av;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ArrayList<String> notesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Randu ekrano komponentus
        ListView listView = findViewById(R.id.listView);
        Button buttonAddNote = findViewById(R.id.buttonAddNote);
        Button buttonDeleteNote = findViewById(R.id.buttonDeleteNote);

        // Nustatau vietą užrašų saugojimui
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        // Sukuriu užrašų sąrašą
        notesList = new ArrayList<>();
        loadNotes(); // Užkraunu užrašus

        // Sukuriu ListView adapterį
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(adapter);

        // Darau veiksmą su mygtuku "Add Note"
        buttonAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivity(intent); // Perėjimas į AddNoteActivity
        });

        // Darau veiksmą su mygtuku "Delete Note"
        buttonDeleteNote.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeleteNoteActivity.class);
            startActivity(intent); // Perėjimas į DeleteNoteActivity
        });

        // Darau veiksmą su užrašo pasirinkimu
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedNote = notesList.get(position).split(":")[0]; // Išskiriu užrašo pavadinimą
            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("noteName", selectedNote); // Perduodu užrašo pavadinimą
            startActivity(intent); // Perėjimas į ViewNoteActivity
        });
    }

    // Užkraunu užrašus iš SharedPreferences į sąrašą
    private void loadNotes() {
        notesList.clear(); // Išvalau esamą sąrašą
        for (String key : sharedPreferences.getAll().keySet()) {
            String content = sharedPreferences.getString(key, ""); // Gaunu užrašo turinį
            notesList.add(key + ": " + content); // Įdedu užrašą formatu "Pavadinimas: Turinys"
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // Užkraunu užrašus grįžus į šį ekraną
        adapter.notifyDataSetChanged(); // Pranešu adapteriui apie pasikeitimus
    }
}
