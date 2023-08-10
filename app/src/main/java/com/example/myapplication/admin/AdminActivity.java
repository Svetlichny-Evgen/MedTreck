package com.example.myapplication.admin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.*;
import com.example.myapplication.doctor.Doctor;
import com.example.myapplication.doctor.DoctorInfo;
import com.example.myapplication.nurse.Nurse;
import com.example.myapplication.nurse.NurseInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    ArrayList<Admin> admins = new ArrayList<>();
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Nurse> nurses = new ArrayList<>();
    TextView full_name;
    private FloatingActionButton fab;
    private int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        full_name = findViewById(R.id.full_name);
        String fio = getIntent().getStringExtra("fio");
        full_name.setText(fio);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        TableRow headerRow = new TableRow(this);

        Cursor nurseCursor = myDB.getAllNursesData();
        if (nurseCursor.getCount() > 0) {
            while (nurseCursor.moveToNext()) {
                Nurse nurse = new Nurse(Integer.parseInt(nurseCursor.getString(0)),
                        nurseCursor.getString(1),
                        nurseCursor.getString(2),
                        nurseCursor.getString(3),
                        nurseCursor.getString(4),
                        nurseCursor.getString(5),
                        Integer.parseInt(nurseCursor.getString(6)));
                nurses.add(nurse);
            }
        }

        Cursor doctorCursor = myDB.getAllDoctorsData();
        if (doctorCursor.getCount() > 0) {
            while (doctorCursor.moveToNext()) {
                Doctor doctor = new Doctor(Integer.parseInt(doctorCursor.getString(0)),
                        doctorCursor.getString(1),
                        doctorCursor.getString(2),
                        doctorCursor.getString(3),
                        doctorCursor.getString(4),
                        Integer.parseInt(doctorCursor.getString(5)),
                        doctorCursor.getString(6));
                doctors.add(doctor);
            }
        }

        Cursor adminCursor = myDB.getAllAdminsData();
        if (adminCursor.getCount() > 0) {
            while (adminCursor.moveToNext()) {
                Admin admin = new Admin(Integer.parseInt(adminCursor.getString(0)),
                        adminCursor.getString(1),
                        adminCursor.getString(2),
                        adminCursor.getString(3));
                admins.add(admin);
            }
        }


        TableLayout tableLayout = findViewById(R.id.tableLayout);

        if (full_name.getText().toString().equals("Світличний Євген")) {
            TextView headerName = new TextView(this);
            headerName.setText("Ім'я");
            headerName.setTextSize(25);
            headerName.setTypeface(null, Typeface.BOLD);
            headerName.setPadding(10, 10, 10, 10);
            headerRow.addView(headerName);

            tableLayout.addView(headerRow); // добавление строки заголовка таблицы

            for (int i = 1; i < admins.size(); i++) {
                TableRow row = new TableRow(this);

                TextView name = new TextView(this);
                name.setText(admins.get(i).getName());
                name.setTextSize(20);
                name.setPadding(10, 5, 10, 5); // установка отступов для текста

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Действия при нажатии на TextView
                        Toast.makeText(getApplicationContext(), "Ви натиснули на " + ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this, AdminInfo.class);
                        intent.putExtra("admin_name", ((TextView)v).getText().toString());
                        startActivity(intent);
                    }
                });

                row.addView(name);

                tableLayout.addView(row); // добавление строки данных таблицы
            }
        }

        else {
            String adminName = full_name.getText().toString();
            Cursor cursor = myDB.getAdminIdByName(adminName);

            if (cursor != null && cursor.moveToFirst()) {
                adminId = cursor.getInt(0);
                // Perform necessary operations with the admin ID
                cursor.close();
            }


            TextView headerName = new TextView(this);
            headerName.setText("Ім'я");
            headerName.setTextSize(25);
            headerName.setTypeface(null, Typeface.BOLD);
            headerName.setPadding(10, 10, 10, 10);
            headerRow.addView(headerName);

            TextView headerPosition = new TextView(this);
            headerPosition.setText("Посада");
            headerPosition.setTextSize(25);
            headerPosition.setTypeface(null, Typeface.BOLD);
            headerPosition.setPadding(10, 10, 10, 10);
            headerRow.addView(headerPosition);

            tableLayout.addView(headerRow); // добавление строки заголовка таблицы

            for (int i = 0; i < doctors.size(); i++) {
                TableRow row = new TableRow(this);

                TextView name = new TextView(this);
                name.setText(doctors.get(i).getName());
                name.setTextSize(20);
                name.setPadding(10, 5, 10, 5);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Действия при нажатии на TextView
                        Toast.makeText(getApplicationContext(), "Ви натиснули на " + ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this, DoctorInfo.class);
                        intent.putExtra("doctor_name", ((TextView)v).getText().toString());
                        startActivity(intent);
                    }
                });
                row.addView(name);

                TextView position = new TextView(this);
                position.setText("Лікар");
                position.setTextSize(20);
                position.setPadding(10, 5, 10, 5);
                row.addView(position);

                tableLayout.addView(row);
            }

            for (int i = 0; i < nurses.size(); i++) {
                TableRow row = new TableRow(this);

                TextView name = new TextView(this);
                name.setText(nurses.get(i).getName());
                name.setTextSize(20);
                name.setPadding(10, 5, 10, 5);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Действия при нажатии на TextView
                        Toast.makeText(getApplicationContext(), "Ви натиснули на " + ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this, NurseInfo.class);
                        intent.putExtra("nurse_name", ((TextView)v).getText().toString());
                        startActivity(intent);
                    }
                });
                row.addView(name);

                TextView position = new TextView(this);
                position.setText("Мед. Сестра");
                position.setTextSize(20);
                position.setPadding(10, 5, 10, 5);
                row.addView(position);

                tableLayout.addView(row); // добавление строки данных таблицы
            }
        }

        fab = findViewById(R.id.floatingActionButton2);
        if (full_name.getText().toString().equals("Світличний Євген")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Функциональность для супер-админа
                    Intent intent = new Intent(AdminActivity.this, AddAdmin.class);
                    startActivity(intent);
                }
            });
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Функциональность для других ролей
                    Intent intent = new Intent(AdminActivity.this, Choice.class);
                    intent.putExtra("admin_id", adminId);
                    startActivity(intent);
                }
            });
        }
    }

}