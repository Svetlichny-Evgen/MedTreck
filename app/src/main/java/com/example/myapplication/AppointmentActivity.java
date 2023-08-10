package com.example.myapplication;

import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentActivity extends AppCompatActivity {

    Spinner medSpinner;
    Spinner procSpinner;
    Button add;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appiontment);

        int nurse_id = getIntent().getIntExtra("nurse_id", 0);
        int doctor_id = getIntent().getIntExtra("doctor_id", 0);
        int patient_id = getIntent().getIntExtra("patient_id", 0);

        medSpinner = findViewById(R.id.medications_spinner);
        procSpinner = findViewById(R.id.procedures_spinner);
        add = findViewById(R.id.add_appointment_record);

        databaseHelper = new DatabaseHelper(this);

        List<String> medications = getAllMedications();
        List<String> procedures = getAllProcedures();

        // Заполнение Spinner медикаментами
        ArrayAdapter<String> medAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medications);
        medAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medSpinner.setAdapter(medAdapter);

        // Заполнение Spinner процедурами
        ArrayAdapter<String> procAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, procedures);
        procAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        procSpinner.setAdapter(procAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedMedication = medSpinner.getSelectedItem().toString();
                String selectedProcedure = procSpinner.getSelectedItem().toString();

                int medicationId = getMedicationId(selectedMedication);
                int procedureId = getProcedureId(selectedProcedure);

                // Получение текущей даты
                String currentDate = getCurrentDate();

                boolean result = databaseHelper.addAppointmentRecord(patient_id, doctor_id, nurse_id, medicationId, procedureId, currentDate);
            }
        });
    }

    private List<String> getAllMedications() {
        List<String> medications = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllMedicationData();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int medicationNameIndex = cursor.getColumnIndexOrThrow("medication_name");
                String medicationName = cursor.getString(medicationNameIndex);
                medications.add(medicationName);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return medications;
    }

    private List<String> getAllProcedures() {
        List<String> procedures = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllProcedureData();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int procedureNameIndex = cursor.getColumnIndexOrThrow("procedure_name");
                String procedureName = cursor.getString(procedureNameIndex);
                procedures.add(procedureName);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return procedures;
    }

    private int getMedicationId(String medicationName) {
        int medicationId = 0;
        Cursor cursor = databaseHelper.getMedicationByName(medicationName);
        if (cursor != null && cursor.moveToFirst()) {
            int medicationIdIndex = cursor.getColumnIndexOrThrow("medication_id");
            medicationId = cursor.getInt(medicationIdIndex);
            cursor.close();
        }
        return medicationId;
    }

    private int getProcedureId(String procedureName) {
        int procedureId = 0;
        Cursor cursor = databaseHelper.getProcedureByName(procedureName);
        if (cursor != null && cursor.moveToFirst()) {
            int procedureIdIndex = cursor.getColumnIndexOrThrow("procedure_id");
            procedureId = cursor.getInt(procedureIdIndex);
            cursor.close();
        }
        return procedureId;
    }
    // Метод для получения текущей даты в формате "гггг-мм-дд"
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }
}