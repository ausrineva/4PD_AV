package com.example.a4pd_av;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ArrayList<String> noteNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        // Randu UI komponentus ekrane
        Spinner spinnerNotes = findViewById(R.id.spinnerNotes);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonBack = findViewById(R.id.buttonBackToMainFromDelete); // Nustatau grįžimo mygtuką

        // Sukuriu vietą užrašų saugojimui
        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);

        // Užpildau Spinner elementą užrašais
        loadNoteNames();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noteNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotes.setAdapter(spinnerAdapter);

        // Atlieku veiksmą paspaudus mygtuką "Delete"
        buttonDelete.setOnClickListener(view -> {
            String selectedNote = (String) spinnerNotes.getSelectedItem(); // Pasiimu pasirinkto užrašo pavadinimą

            if (selectedNote != null) {
                // Pašalinu užrašą iš saugyklos
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(selectedNote);
                editor.apply();

                // Informuoju vartotoją apie sėkmingą pašalinimą
                Toast.makeText(this, "Note deleted!", Toast.LENGTH_SHORT).show();

                // Atnaujinu Spinner turinį
                loadNoteNames();
                spinnerAdapter.notifyDataSetChanged();

                // Grąžinu vartotoją atgal
                finish();
            } else {
                // Parodau žinutę, jei nėra pasirinkto užrašo
                Toast.makeText(this, "No note selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Grįžimo mygtuko veikimas
        buttonBack.setOnClickListener(view -> finish()); // Grįžtu į ankstesnį ekraną
    }

    // Užpildau sąrašą užrašų pavadinimais
    private void loadNoteNames() {
        noteNames = new ArrayList<>();
        Map<String, ?> notes = sharedPreferences.getAll(); // Pasiimu visus užrašus
        noteNames.addAll(notes.keySet()); // Sudedu užrašų pavadinimus į sąrašą
    }
}
