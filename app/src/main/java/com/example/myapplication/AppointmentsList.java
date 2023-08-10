package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.nurse.NurseActivity;
import com.example.myapplication.patient.Appointment;
import com.example.myapplication.patient.Patient;
import com.example.myapplication.patient.PatientInfo;

import java.util.ArrayList;

public class AppointmentsList extends AppCompatActivity {

    ArrayList<Appointment> appointments = new ArrayList<>();
    DatabaseHelper myDB;
    TextView fio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_list);

        int patient_id = getIntent().getIntExtra("patient_id", 0);

        Context context = getApplicationContext();
        myDB = new DatabaseHelper(context);

        Cursor appointmentCursor = myDB.getAppointmentData(patient_id);
        if (appointmentCursor.getCount() > 0) {
            while (appointmentCursor.moveToNext()) {
                Appointment appointment = new Appointment(Integer.parseInt(appointmentCursor.getString(0)),
                        Integer.parseInt(appointmentCursor.getString(1)),
                        Integer.parseInt(appointmentCursor.getString(2)),
                        Integer.parseInt(appointmentCursor.getString(3)),
                        Integer.parseInt(appointmentCursor.getString(4)),
                        Integer.parseInt(appointmentCursor.getString(5)),
                        appointmentCursor.getString(6));
                appointments.add(appointment);
            }
        }

        fio = findViewById(R.id.patient_name_textview);
        setPatientName(patient_id);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (int i = 0; i < appointments.size(); i++) {
            TableRow row = new TableRow(this);

            TextView date = new TextView(this);
            date.setText(appointments.get(i).getAppointmentDate());
            date.setTextSize(20);
            date.setPadding(10, 5, 10, 5);
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Действия при нажатии на TextView
                    Toast.makeText(getApplicationContext(), "Ви натиснули на " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                    int index = tableLayout.indexOfChild((TableRow) v.getParent());
                    if (index >= 0 && index < appointments.size()) {
                        Appointment selectedAppointment = appointments.get(index);
                        Intent intent = new Intent(AppointmentsList.this, AppointmentInfo.class);
                        intent.putExtra("appointment_id", selectedAppointment.getId());
                        startActivity(intent);
                    }
                }
            });
            row.addView(date);
            tableLayout.addView(row); // добавление строки данных таблицы
        }
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активити и возвращаемся на предыдущую
            }
        });
    }

    private void setPatientName(int patientId) {
        Cursor cursor = myDB.getPatientNameById(patientId);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("patient_name");
            if (nameIndex != -1) {
                String name = cursor.getString(nameIndex);
                fio.setText(name);
            }
            cursor.close();
        }
    }
}