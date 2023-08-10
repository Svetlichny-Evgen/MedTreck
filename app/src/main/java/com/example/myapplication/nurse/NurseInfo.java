package com.example.myapplication.nurse;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.Objects;

public class NurseInfo extends AppCompatActivity {

    TextView name;
    TextView email;
    Button back;
    Button delete;
    Nurse nurse = new Nurse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_info);

        String nurse_name = getIntent().getStringExtra("nurse_name");

        name = findViewById(R.id.full_name_textview);
        email = findViewById(R.id.email_textview);
        delete = findViewById(R.id.delete_button);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        Cursor cursor = myDB.getNurseDataByName(nurse_name);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nurse.setId(Integer.parseInt(cursor.getString(0)));
                nurse.setName(cursor.getString(1));
                nurse.setEmail(cursor.getString(2));
                nurse.setPassword(cursor.getString(3));
                nurse.setShiftStart(cursor.getString(4));
                nurse.setShiftEnd(cursor.getString(5));
                nurse.setAdminId(Integer.parseInt(cursor.getString(6)));
            }
        }

        name.setText(nurse.getName());
        email.setText(nurse.getEmail());

        delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteNurseById(nurse.getId());
            }
        });

        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активити и возвращаемся на предыдущую
            }
        });
    }
}