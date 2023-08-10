package com.example.myapplication.doctor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.*;
import com.example.myapplication.patient.AddPatient;
import com.example.myapplication.patient.Patient;
import com.example.myapplication.patient.PatientInfo;
import com.example.myapplication.patient.Ward;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    TextView full_name;
    ArrayList<Patient> patients = new ArrayList<>();
    Doctor doctor = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        full_name = findViewById(R.id.full_name);
        String fio = getIntent().getStringExtra("fio");
        full_name.setText(fio);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        Cursor cursor = myDB.getDoctorDataByName(fio);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                doctor.setId(Integer.parseInt(cursor.getString(0)));
                doctor.setName(cursor.getString(1));
                doctor.setSpecification(cursor.getString(2));
                doctor.setPhone(cursor.getString(3));
                doctor.setEmail(cursor.getString(4));
                doctor.setAdminId(Integer.parseInt(cursor.getString(5)));
                doctor.setPassword(cursor.getString(6));
            }
        }

        Cursor patientsCursor = myDB.getPatientsByDoctor(doctor.getId());
        if (patientsCursor.getCount() > 0) {
            while (patientsCursor.moveToNext()) {
                Patient patient = new Patient(Integer.parseInt(patientsCursor.getString(0)),
                        patientsCursor.getString(1),
                        patientsCursor.getString(2),
                        patientsCursor.getString(3),
                        patientsCursor.getString(4),
                        Integer.parseInt(patientsCursor.getString(5)),
                        Integer.parseInt(patientsCursor.getString(6)));
                patients.add(patient);
            }
        }

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (int i = 0; i < patients.size(); i++) {
            TableRow row = new TableRow(this);

            TextView name = new TextView(this);
            name.setText(patients.get(i).getName());
            name.setTextSize(20);
            name.setPadding(10, 5, 10, 5);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Действия при нажатии на TextView
                    Toast.makeText(getApplicationContext(), "Ви натиснули на " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorActivity.this, PatientInfo.class);
                    intent.putExtra("patient_name", ((TextView) v).getText().toString());
                    intent.putExtra("doctor_id", doctor.getId());
                    startActivity(intent);
                }
            });
            row.addView(name);
            tableLayout.addView(row); // добавление строки данных таблицы
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, AddPatient.class);
                intent.putExtra("doctor_id", doctor.getId());
                startActivity(intent);
            }
        });
    }
}