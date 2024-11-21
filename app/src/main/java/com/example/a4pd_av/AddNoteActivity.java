package com.example.a4pd_av;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Inicializuoju UI komponentus
        EditText editTextNoteName = findViewById(R.id.editTextNoteName);
        EditText editTextNoteContent = findViewById(R.id.editTextNoteContent);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonBackToMainFromAdd = findViewById(R.id.buttonBackToMainFromAdd);

        // Sukuriu SharedPreferences saugyklą
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        // Nustatau "Save" mygtuko veikimą
        buttonSave.setOnClickListener(view -> {
            // Paimu tekstą iš EditText laukų
            String noteName = editTextNoteName.getText().toString().trim();
            String noteContent = editTextNoteContent.getText().toString().trim();

            // Patikrinu, ar laukai užpildyti
            if (noteName.isEmpty() || noteContent.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Išsaugau duomenis į SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(noteName, noteContent);
            editor.apply();

            // Parodau vartotojui pranešimą ir uždarau veiklą
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Nustatau "Back" mygtuko veikimą
        buttonBackToMainFromAdd.setOnClickListener(view -> {
            // Grįžtu į ankstesnį ekraną
            finish();
        });
    }
}
