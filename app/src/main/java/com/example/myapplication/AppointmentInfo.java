package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.patient.Appointment;

public class AppointmentInfo extends AppCompatActivity {

    TextView patient_name;
    TextView doc_name;
    TextView nurse_name;
    TextView med_name;
    TextView proc_name;
    TextView date;

    TextView titleDoc_name;
    TextView titleNurse_name;

    Appointment appo = new Appointment();
    DatabaseHelper myDB;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);

        int appointment_id = getIntent().getIntExtra("appointment_id", 0);

        patient_name = findViewById(R.id.patientNameTextView);
        doc_name = findViewById(R.id.doctorNameTextView);
        nurse_name = findViewById(R.id.nurseNameTextView);
        med_name = findViewById(R.id.medicationNameTextView);
        proc_name = findViewById(R.id.procedureNameTextView);
        date = findViewById(R.id.procedureDateTextView);
        titleDoc_name = findViewById(R.id.doctorNameTitleTextView);
        titleNurse_name = findViewById(R.id.nurseNameTitleTextView);
        back = findViewById(R.id.backButton);

        Context context = getApplicationContext();
        myDB = new DatabaseHelper(context);

        Cursor cursor = myDB.getAppointmentById(appointment_id);
        if (cursor != null && cursor.moveToFirst()) {
            int patientNameIndex = cursor.getColumnIndex("patient_id");
            int doctorNameIndex = cursor.getColumnIndex("doctor_id");
            int nurseIdIndex = cursor.getColumnIndex("nurse_id");
            int medicationIdIndex = cursor.getColumnIndex("medication_id");
            int procedureIdIndex = cursor.getColumnIndex("procedure_id");
            int appoDateIndex = cursor.getColumnIndex("appointment_date");

            if (patientNameIndex >= 0) {
                int patient_id = cursor.getInt(patientNameIndex);
                setPatientName(patient_id);
            }
            if (doctorNameIndex >= 0) {
                int doctor_id = cursor.getInt(doctorNameIndex);
                setDoctorName(doctor_id);
            }
            if (nurseIdIndex >= 0) {
                int nurseId = cursor.getInt(nurseIdIndex);
                setNurseName(nurseId);
            }
            if (medicationIdIndex >= 0) {
                int medicationId = cursor.getInt(medicationIdIndex);
                setMedicationName(medicationId);
            }
            if (procedureIdIndex >= 0) {
                int procedureId = cursor.getInt(procedureIdIndex);
                setProcedureName(procedureId);
            }
            if (appoDateIndex >= 0) {
                String appoDate = cursor.getString(appoDateIndex);
                date.setText(appoDate);
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активити и возвращаемся на предыдущую
            }
        });
    }

    private void setPatientName(int patient_id) {
        Cursor cursor = myDB.getPatientNameById(patient_id);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("patient_name");
            if (nameIndex != -1) {
                String name = cursor.getString(nameIndex);
                patient_name.setText(name);
            }
            cursor.close();
        }
    }

    private void setDoctorName(int doctor_id) {
        Cursor cursor = myDB.getDoctorNameById(doctor_id);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("doctor_name");
                if (nameIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    doc_name.setText(name);
                }
            } else {
                // Если курсор пустой, скрываем TextView doc_name
                doc_name.setVisibility(View.GONE);
                titleDoc_name.setVisibility(View.GONE);
            }
            cursor.close();
        } else {
            // Если курсор равен null, скрываем TextView doc_name
            doc_name.setVisibility(View.GONE);
            titleDoc_name.setVisibility(View.GONE);
        }
    }


    private void setNurseName(int nurseId) {
        Cursor cursor = myDB.getNurseNameById(nurseId);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("name");
                if (nameIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    nurse_name.setText(name);
                }
            } else {
                // Если курсор пустой, скрываем TextView nurse_name
                nurse_name.setVisibility(View.GONE);
                titleNurse_name.setVisibility(View.GONE);
            }
            cursor.close();
        } else {
            // Если курсор равен null, скрываем TextView nurse_name
            nurse_name.setVisibility(View.GONE);
            titleNurse_name.setVisibility(View.GONE);
        }
    }

    private void setMedicationName(int medicationId) {
        Cursor cursor = myDB.getMedicationNameById(medicationId);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("medication_name");
            if (nameIndex != -1) {
                String name = cursor.getString(nameIndex);
                med_name.setText(name);
            }
            cursor.close();
        }
    }

    private void setProcedureName(int procedureId) {
        Cursor cursor = myDB.getProcedureNameById(procedureId);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("procedure_name");
            if (nameIndex != -1) {
                String name = cursor.getString(nameIndex);
                proc_name.setText(name);
            }
            cursor.close();
        }
    }
}