package com.example.myapplication.patient;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AddPatient extends AppCompatActivity {

    private EditText cardNumberEditText;
    private EditText nameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Spinner patientRoomSpinner;
    private Button addButton;
    ArrayList<Ward> wards = new ArrayList<>();
    Ward ward = new Ward();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        cardNumberEditText = findViewById(R.id.patient_card_number_edittext);
        nameEditText = findViewById(R.id.patient_name_edittext);
        addressEditText = findViewById(R.id.patient_address_edittext);
        phoneEditText = findViewById(R.id.patient_phone_edittext);
        emailEditText = findViewById(R.id.patient_email_edittext);
        patientRoomSpinner = findViewById(R.id.patient_room_spinner);
        addButton = findViewById(R.id.add_patient_button);


        Cursor availableWards = myDB.getAvailableWards();

// Создаем список для отображения в Spinner
        List<String> wardNumbers = new ArrayList<>();
        if (availableWards != null && availableWards.moveToFirst()) {
            do {
                int wardIdIndex = availableWards.getColumnIndexOrThrow("ward_id");
                int wardId = availableWards.getInt(wardIdIndex);
                wardNumbers.add(String.valueOf(wardId));
            } while (availableWards.moveToNext());
            availableWards.close();
        }

// Создаем адаптер для Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Устанавливаем адаптер для Spinner
        patientRoomSpinner.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNumber = cardNumberEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String patientRoom = patientRoomSpinner.getSelectedItem().toString();
                int doctor_id = getIntent().getIntExtra("doctor_id", 0);

                boolean result = myDB.addPatientData(Integer.valueOf(cardNumber), name, address, phone, email, doctor_id, Integer.valueOf(patientRoom));

                if (result) {
                    Toast.makeText(AddPatient.this, "Пацієнт був успішно доданий", Toast.LENGTH_SHORT).show();
                    // Здесь можно выполнить дополнительные действия после успешного добавления пациента
                    myDB.incrementWardCount(Integer.valueOf(patientRoom));
                } else {
                    Toast.makeText(AddPatient.this, "Помилка при додаванні пацієнта", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}