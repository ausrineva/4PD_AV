package com.example.a4pd_av;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        // Randu ekrano komponentus
        TextView textViewNoteName = findViewById(R.id.textViewNoteName);
        TextView textViewNoteContent = findViewById(R.id.textViewNoteContent);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        // Nustatau vietą užrašų saugojimui
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        // Pasiimu užrašo pavadinimą, kuris buvo perduotas iš ankstesnės veiklos
        Intent intent = getIntent();
        String noteName = intent.getStringExtra("noteName");
        String noteContent = sharedPreferences.getString(noteName, "");

        // Rodyti užrašo informaciją ekrane
        textViewNoteName.setText(noteName);
        textViewNoteContent.setText(noteContent);

        // Darau veiksmą su grįžimo mygtuku
        buttonBack.setOnClickListener(view -> finish());

        // Darau veiksmą su ištrynimo mygtuku
        buttonDelete.setOnClickListener(view -> {
            if (noteName != null) {
                // Šalinu užrašą iš SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(noteName);
                editor.apply();

                // Rodyti pranešimą apie sėkmingą ištrynimą
                Toast.makeText(this, "Note deleted!", Toast.LENGTH_SHORT).show();
                finish(); // Grįžtu į ankstesnį ekraną
            } else {
                // Rodyti pranešimą, jei užrašas nerastas
                Toast.makeText(this, "Error: Note not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
