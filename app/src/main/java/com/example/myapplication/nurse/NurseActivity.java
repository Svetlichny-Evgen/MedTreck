package com.example.myapplication.nurse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.Choice;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.admin.AdminActivity;
import com.example.myapplication.doctor.DoctorActivity;
import com.example.myapplication.patient.Patient;
import com.example.myapplication.patient.PatientInfo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class NurseActivity extends AppCompatActivity {

    TextView full_name;
    Button start;
    ArrayList<Patient> patients = new ArrayList<>();
    Nurse nurse = new Nurse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);

        full_name = findViewById(R.id.full_name);
        String fio = getIntent().getStringExtra("fio");
        full_name.setText(fio);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        Cursor cursor = myDB.getNurseDataByName(fio);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nurse.setId(Integer.parseInt(cursor.getString(0)));
                nurse.setName(cursor.getString(1));
                nurse.setEmail(cursor.getString(2));
                nurse.setPassword(cursor.getString(3));
                nurse.setShiftStart(cursor.getString(4));
                nurse.setShiftEnd(cursor.getString(5));
                nurse.setAdminId(Objects.nonNull(cursor.getString(6))?Integer.parseInt(cursor.getString(6)):0);
            }
        }


        Cursor patientsCursor = myDB.getAllPatients();
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
                    Intent intent = new Intent(NurseActivity.this, PatientInfo.class);
                    intent.putExtra("patient_name", ((TextView) v).getText().toString());
                    intent.putExtra("nurse_id", nurse.getId());
                    startActivity(intent);
                }
            });
            row.addView(name);
            tableLayout.addView(row); // добавление строки данных таблицы
        }
        start = findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение текущей даты
                LocalDate currentDate = LocalDate.now();

                // Получение текущей даты плюс один день
                LocalDate shiftEndDate = currentDate.plusDays(1);

                // Преобразование дат в строки для записи в базу данных
                String shiftStart = currentDate.toString();
                String shiftEnd = shiftEndDate.toString();

                // Обновление значений shiftStart и shiftEnd в базе данных для объекта nurse
                nurse.setShiftStart(shiftStart);
                nurse.setShiftEnd(shiftEnd);
                myDB.updateShiftDates(nurse.getId());

                // Проверка, если текущая дата больше shiftEnd
                if (currentDate.isAfter(shiftEndDate)) {
                    // Скрытие кнопки
                    start.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Чергування закінчено", Toast.LENGTH_LONG).show();
                } else {
                    // Расчет и вывод оставшегося времени до завершения дежурства
                    Period remainingPeriod = Period.between(currentDate, shiftEndDate);
                    String remainingTime = String.format("залишився %d день до завершення чергування", remainingPeriod.getDays());
                    Toast.makeText(getApplicationContext(), remainingTime, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}