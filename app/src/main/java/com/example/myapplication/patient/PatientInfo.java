package com.example.myapplication.patient;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.AppointmentActivity;
import com.example.myapplication.AppointmentsList;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

public class PatientInfo extends AppCompatActivity {

    private TextView fullNameTextView;
    private TextView medicalCardNumberTextView;
    private TextView addressTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    private TextView roomNumberTextView;
    private Button backButton;
    private Button deleteButton;
    private Button apointmentButton;
    private Button appointmentsListButton;
    Patient patient = new Patient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        int nurse_id = getIntent().getIntExtra("nurse_id", 0);
        int doctor_id = getIntent().getIntExtra("doctor_id", 0);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        // Получение переданных данных
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String patientName = extras.getString("patient_name");

            // Присваивание значения полученного имени пациента в TextView
            fullNameTextView = findViewById(R.id.full_name_textview);
            fullNameTextView.setText(patientName);

            Cursor cursor = myDB.getPatientDataByName(patientName);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    patient.setId(Integer.parseInt(cursor.getString(0)));
                    patient.setName(cursor.getString(1));
                    patient.setAddress(cursor.getString(2));
                    patient.setPhone(cursor.getString(3));
                    patient.setEmail(cursor.getString(4));
                    patient.setDoctorId(Integer.parseInt(cursor.getString(5)));
                    patient.setPatientRoom(Integer.parseInt(cursor.getString(6)));
                }
            }
            medicalCardNumberTextView = findViewById(R.id.medical_card_number_textview);
            medicalCardNumberTextView.setText(String.valueOf(patient.getId()));

            addressTextView = findViewById(R.id.address_textview);
            addressTextView.setText(patient.getAddress());

            phoneNumberTextView = findViewById(R.id.phone_number_textview);
            phoneNumberTextView.setText(patient.getPhone());

            emailTextView = findViewById(R.id.email_textview);
            emailTextView.setText(patient.getEmail());

            roomNumberTextView = findViewById(R.id.room_number_textview);
            roomNumberTextView.setText(String.valueOf(patient.getPatientRoom()));

            apointmentButton = findViewById(R.id.appointments_button);
            apointmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
// Создание Intent
                    Intent intent = new Intent(PatientInfo.this, AppointmentActivity.class);

// Передача значений nurse_id и doctor_id
                    intent.putExtra("nurse_id", nurse_id);
                    intent.putExtra("doctor_id", doctor_id);
                    intent.putExtra("patient_id", patient.getId());

// Запуск новой активити
                    startActivity(intent);

                }
            });

            deleteButton = findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDB.deletePatientById(patient.getId());
                    myDB.decrementWardCount(patient.getPatientRoom());
                }
            });

            backButton = findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Закрываем текущую активити и возвращаемся на предыдущую
                }
            });

            appointmentsListButton = findViewById(R.id.appointmentsList_button);
            appointmentsListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Создание Intent
                    Intent intent = new Intent(PatientInfo.this, AppointmentsList.class);
                    intent.putExtra("patient_id", patient.getId());
                    // Запуск новой активити
                    startActivity(intent);
                }
            });
        }
    }
}